package dev.eislyn.AutoTodo.controllers;

import dev.eislyn.AutoTodo.domain.dto.LoginRequestDto;
import dev.eislyn.AutoTodo.domain.dto.PasswordDto;
import dev.eislyn.AutoTodo.domain.dto.RegisterRequestDto;
import dev.eislyn.AutoTodo.domain.entities.GenericResponse;
import dev.eislyn.AutoTodo.domain.entities.PasswordResetToken;
import dev.eislyn.AutoTodo.domain.entities.UserEntity;
import dev.eislyn.AutoTodo.domain.entities.VerificationToken;
import dev.eislyn.AutoTodo.events.OnRegistrationCompleteEvent;
import dev.eislyn.AutoTodo.services.IUserAuthService;
import dev.eislyn.AutoTodo.services.impl.TokenServiceImpl;
import dev.eislyn.AutoTodo.services.impl.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final TokenServiceImpl tokenService;
    private final AuthenticationManager authenticationManager;
    private final ApplicationEventPublisher eventPublisher;
    private final UserDetailsServiceImpl userDetailsService;
    private final IUserAuthService userAuthService;
    private final JavaMailSender mailSender;
    private final MessageSource messageSource;
    private final Environment env;

    @Value("${APP_URL}")
    private String appUrl;

    public AuthController(TokenServiceImpl tokenService, AuthenticationManager authenticationManager, ApplicationEventPublisher eventPublisher, UserDetailsServiceImpl userDetailsService, IUserAuthService userAuthService, JavaMailSender mailSender, MessageSource messageSource, Environment env) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userAuthService = userAuthService;
        this.eventPublisher = eventPublisher;
        this.userDetailsService = userDetailsService;
        this.mailSender = mailSender;
        this.messageSource = messageSource;
        this.env = env;
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponse<String>> register(HttpServletRequest request, @RequestBody RegisterRequestDto registerRequest) {
        try {
            UserEntity registeredUser = userAuthService.registerUser(registerRequest);
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser, request.getLocale(), appUrl));
            return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>("success", "User registered successfully", null));
        } catch (Exception e) {
            log.error("Registration failed: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponse<>("error", "Registration failed: " + e.getMessage(), null));
        }
    }

    @GetMapping("/registrationConfirm")
    public ResponseEntity<GenericResponse<String>> confirmRegistration(WebRequest request, @RequestParam("token") String token) {
        Locale locale = request.getLocale();

        VerificationToken verificationToken = userAuthService.getVerificationToken(token);
        if (verificationToken == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse<>("error", "Invalid or expired token. Please request again.", null));
        }

        UserEntity user = verificationToken.getUser();
        if (user.isEnabled()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new GenericResponse<>("error", "User is already verified.", null));
        }

        user.setEnabled(true);
        userAuthService.saveRegisteredUser(user);

        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>("success", "Email verified successfully.", null));
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<String>> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(new GenericResponse<>("success", "Login successful", token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GenericResponse<>("error", "Invalid credentials", null));
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<GenericResponse<String>> resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
        UserEntity user = userAuthService.findUserByEmail(userEmail);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse<>("error", "User not found, please register.", null));
        }
        PasswordResetToken token = userAuthService.createPasswordResetTokenForUser(user);
        userAuthService.sendResetPasswordEmail(user, request, appUrl, token.getToken());

        return ResponseEntity.ok(new GenericResponse<>("success", "Password reset email sent successfully", null));
    }

    @PostMapping("/savePassword")
    public ResponseEntity<GenericResponse<String>> savePassword(final Locale locale, @RequestBody PasswordDto passwordDto) {
        String result = userAuthService.validatePasswordResetToken(passwordDto.getToken());

        if(result != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new GenericResponse<>("error", "Reset password token is invalid or expired. Please request again.", null));
        }

        Optional<UserEntity> user = userAuthService.getUserByPasswordResetToken(passwordDto.getToken());
        if(user.isPresent()) {
            userAuthService.changeUserPassword(user.get(), passwordDto.getNewPassword());
            return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>("success", "Password is reset successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse<>("error", "User cannot be found, please register.", null));
        }
    }
}
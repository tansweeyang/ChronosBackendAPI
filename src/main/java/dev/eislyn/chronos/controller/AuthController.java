package dev.eislyn.chronos.controller;

import dev.eislyn.chronos.Exceptions.UserVerifiedException;
import dev.eislyn.chronos.dto.LoginRequestDto;
import dev.eislyn.chronos.dto.PasswordDto;
import dev.eislyn.chronos.dto.RegisterRequestDto;
import dev.eislyn.chronos.dto.UserResponseDto;
import dev.eislyn.chronos.model.GenericResponse;
import dev.eislyn.chronos.model.PasswordResetToken;
import dev.eislyn.chronos.model.UserEntity;
import dev.eislyn.chronos.model.VerificationToken;
import dev.eislyn.chronos.events.OnRegistrationCompleteEvent;
import dev.eislyn.chronos.service.IUserAuthService;
import dev.eislyn.chronos.service.impl.TokenServiceImpl;
import dev.eislyn.chronos.service.impl.UserDetailsServiceImpl;
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

import java.rmi.NoSuchObjectException;
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
    public ResponseEntity<GenericResponse<UserResponseDto>> register(HttpServletRequest request, @Valid @RequestBody RegisterRequestDto registerRequest) {
        try {
            UserEntity registeredUser = userAuthService.registerUser(registerRequest);
            UserResponseDto responseDto = new UserResponseDto(registeredUser.getId(), registeredUser.getEmail(), registeredUser.getUsername());
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser, request.getLocale(), appUrl));
            return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>("success", HttpStatus.CREATED,"User registered successfully", responseDto));
        } catch (IllegalArgumentException e) {
            // Additional data validation
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponse<>("error", HttpStatus.BAD_REQUEST, "Registration failed: " + e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse<>("error", HttpStatus.INTERNAL_SERVER_ERROR, "Registration failed: An unexpected error occurred. Please try again later.", null));
        }
    }

    @GetMapping("/registrationConfirm")
    public ResponseEntity<GenericResponse<String>> confirmRegistration(WebRequest request, @RequestParam("token") String token) {
        Locale locale = request.getLocale();
        try {
            VerificationToken verificationToken = userAuthService.getVerificationToken(token);
            UserEntity user = verificationToken.getUser();
            user.setEnabled(true);
            userAuthService.saveRegisteredUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>("success", HttpStatus.OK, "Email verified successfully.", null));
        } catch (NoSuchObjectException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse<>("error", HttpStatus.NOT_FOUND, "Invalid or expired token. Please request again.", null));
        } catch (UserVerifiedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new GenericResponse<>("error", HttpStatus.FORBIDDEN,"User is already verified.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse<>("error", HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred. Please try again later.", null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<String>> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        log.info("user is enabled", userDetailsService.loadUserByUsername(loginRequest.getUsername()).isEnabled());

        if(!userDetailsService.loadUserByUsername(loginRequest.getUsername()).isEnabled()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new GenericResponse<>("error", HttpStatus.FORBIDDEN, "Login denied. Please confirm your email.", null));
        }
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>("success", HttpStatus.OK, "Login successful", token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GenericResponse<>("error", HttpStatus.UNAUTHORIZED, "Login failed: Wrong username or password. Please try again.", null));
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<GenericResponse<String>> resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
        UserEntity user = userAuthService.findUserByEmail(userEmail);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse<>("error", HttpStatus.NOT_FOUND,"User not found, please register.", null));
        }
        PasswordResetToken token = userAuthService.createPasswordResetTokenForUser(user);
        userAuthService.sendResetPasswordEmail(user, request, appUrl, token.getToken());

        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>("success", HttpStatus.OK,"Password reset email sent successfully", null));
    }

    @PostMapping("/savePassword")
    public ResponseEntity<GenericResponse<String>> savePassword(@Valid @RequestBody PasswordDto passwordDto) {
        if (!passwordDto.getNewPassword().equals(passwordDto.getConfirmationPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GenericResponse<>("error", HttpStatus.BAD_REQUEST,"Password and confirm password do not match.", null));
        }

        String result = userAuthService.validatePasswordResetToken(passwordDto.getToken());

        if(result != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new GenericResponse<>("error", HttpStatus.FORBIDDEN,"Reset password token is invalid or expired. Please request again.", null));
        }

        Optional<UserEntity> user = userAuthService.getUserByPasswordResetToken(passwordDto.getToken());
        if(user.isPresent()) {
            userAuthService.changeUserPassword(user.get(), passwordDto.getNewPassword());
            return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>("success", HttpStatus.OK, "Password is reset successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse<>("error", HttpStatus.NOT_FOUND,"User cannot be found, please register.", null));
        }
    }
}
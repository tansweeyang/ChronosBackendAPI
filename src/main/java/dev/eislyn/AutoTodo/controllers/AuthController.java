package dev.eislyn.AutoTodo.controllers;

import dev.eislyn.AutoTodo.domain.dto.LoginRequestDto;
import dev.eislyn.AutoTodo.domain.dto.RegisterRequestDto;
import dev.eislyn.AutoTodo.domain.entities.UserEntity;
import dev.eislyn.AutoTodo.domain.entities.VerificationToken;
import dev.eislyn.AutoTodo.services.OnRegistrationCompleteEvent;
import dev.eislyn.AutoTodo.services.impl.TokenService;
import dev.eislyn.AutoTodo.services.impl.UserRegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Locale;

@RestController
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final TokenService tokenService;
    private AuthenticationManager authenticationManager;
    private UserRegistrationService userRegistrationService;
    private ApplicationEventPublisher eventPublisher;

    @Value("${APP_URL}")
    private String appUrl;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager, UserRegistrationService userRegistrationService, ApplicationEventPublisher eventPublisher) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userRegistrationService = userRegistrationService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(HttpServletRequest request, @RequestBody RegisterRequestDto registerRequest) {
        try {
            UserEntity registeredUser = userRegistrationService.registerUser(registerRequest);
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser,
                    request.getLocale(), appUrl));
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            log.error("Registration failed: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + e.getMessage());
        }
    }

    @GetMapping("/registrationConfirm")
    public ResponseEntity<String> confirmRegistration(WebRequest request, @RequestParam("token") String token) {
        Locale locale = request.getLocale();

        VerificationToken verificationToken = userRegistrationService.getVerificationToken(token);
        UserEntity user = verificationToken.getUser();

        if (verificationToken == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid or expired token. Please request again.");
        }
        if (user.isEnabled() == true) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is already verified.");
        }

        user.setEnabled(true);
        userRegistrationService.saveRegisteredUser(user);

        return ResponseEntity.status(HttpStatus.OK).body("Email verified successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}

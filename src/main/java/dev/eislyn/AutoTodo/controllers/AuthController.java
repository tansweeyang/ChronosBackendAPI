package dev.eislyn.AutoTodo.controllers;

import dev.eislyn.AutoTodo.domain.dto.LoginRequestDto;
import dev.eislyn.AutoTodo.domain.dto.RegisterRequestDto;
import dev.eislyn.AutoTodo.services.impl.TokenService;
import dev.eislyn.AutoTodo.services.impl.UserRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final TokenService tokenService;
    private AuthenticationManager authenticationManager;
    private UserRegistrationService userRegistrationService;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager, UserRegistrationService userRegistrationService) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto registerRequest) {
        try {
            userRegistrationService.registerUser(registerRequest);  // Assumes you have a service to handle registration
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            log.error("Registration failed: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + e.getMessage());
        }
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

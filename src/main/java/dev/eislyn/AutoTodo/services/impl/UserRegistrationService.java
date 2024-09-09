package dev.eislyn.AutoTodo.services.impl;

import dev.eislyn.AutoTodo.domain.dto.RegisterRequestDto;
import dev.eislyn.AutoTodo.domain.entities.UserEntity;
import dev.eislyn.AutoTodo.repositories.UserRepository;
import dev.eislyn.AutoTodo.services.EmailValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegisterRequestDto registerRequest) {
        // Check if any of the required fields are empty
        if (registerRequest.getUsername() == null || registerRequest.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (registerRequest.getPassword() == null || registerRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (registerRequest.getConfirmationPassword() == null || registerRequest.getConfirmationPassword().isEmpty()) {
            throw new IllegalArgumentException("Confirmation password cannot be empty");
        }

        // Validate email format
        if (!EmailValidator.isValidEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }

        // Check if password and confirmationPassword match
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmationPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        // Check if username already exists
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }

        // Proceed with registration if validations pass
        UserEntity user = new UserEntity();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(user);
    }

}
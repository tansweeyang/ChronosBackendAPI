package dev.eislyn.AutoTodo.services.impl;

import dev.eislyn.AutoTodo.domain.dto.RegisterRequestDto;
import dev.eislyn.AutoTodo.domain.entities.PasswordResetToken;
import dev.eislyn.AutoTodo.domain.entities.UserEntity;
import dev.eislyn.AutoTodo.domain.entities.VerificationToken;
import dev.eislyn.AutoTodo.events.OnPasswordResetRequestEvent;
import dev.eislyn.AutoTodo.repositories.PasswordResetRepository;
import dev.eislyn.AutoTodo.repositories.UserRepository;
import dev.eislyn.AutoTodo.repositories.VerificationTokenRepository;
import dev.eislyn.AutoTodo.services.IUserAuthService;
import dev.eislyn.AutoTodo.utils.EmailValidationUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserAuthServiceImpl implements IUserAuthService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private PasswordResetRepository passwordResetRepository;

    public UserAuthServiceImpl(UserRepository userRepository, VerificationTokenRepository tokenRepository, PasswordEncoder passwordEncoder, PasswordResetRepository passwordResetRepository, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetRepository = passwordResetRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public UserEntity registerUser(RegisterRequestDto registerRequest) {
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

        return userRepository.save(user);
    }

    @Override
    public UserEntity getUser(String verificationToken) {
        UserEntity user = userRepository.findByToken(verificationToken).get();
        return user;
    }

    @Override
    public void saveRegisteredUser(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public void createVerificationToken(UserEntity user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) throws NoSuchObjectException {
        VerificationToken verificationToken = tokenRepository.findByToken(VerificationToken);
        if (verificationToken == null) {
            throw new NoSuchObjectException("Token is null.");
        }
        return verificationToken;
    }

    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    public UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }

    public PasswordResetToken createPasswordResetTokenForUser(UserEntity user) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, expiryDate);
        return passwordResetRepository.save(passwordResetToken);
    }

    public void sendResetPasswordEmail(UserEntity user, HttpServletRequest request, String appUrl, String token) {
        eventPublisher.publishEvent(new OnPasswordResetRequestEvent(user, request.getLocale(), appUrl, token));
    }

    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetRepository.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    public boolean isTokenExpired(PasswordResetToken passToken) {
        return LocalDateTime.now().isAfter(passToken.getExpiryDate());
    }

    public Optional<UserEntity> getUserByPasswordResetToken(String passToken) {
        // Find the PasswordResetToken by the token
        PasswordResetToken passwordResetToken = passwordResetRepository.findByToken(passToken);

        // If the token is found and not expired, return the associated user
        if (passwordResetToken != null && !isTokenExpired(passwordResetToken)) {
            return Optional.of(passwordResetToken.getUser());
        }

        // If the token is not found or is expired, return an empty Optional
        return Optional.empty();
    }

    public void changeUserPassword (UserEntity user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}

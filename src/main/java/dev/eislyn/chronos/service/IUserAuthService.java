package dev.eislyn.chronos.service;

import dev.eislyn.chronos.dto.request.RegisterRequestDto;
import dev.eislyn.chronos.model.PasswordResetToken;
import dev.eislyn.chronos.model.UserEntity;
import dev.eislyn.chronos.model.VerificationToken;
import jakarta.servlet.http.HttpServletRequest;

import java.rmi.NoSuchObjectException;
import java.util.Optional;

public interface IUserAuthService {
    // Register
    UserEntity registerUser(RegisterRequestDto userDto);
    UserEntity getUser(String verificationToken);
    void saveRegisteredUser(UserEntity user);
    void createVerificationToken(UserEntity user, String token);
    VerificationToken getVerificationToken(String VerificationToken) throws NoSuchObjectException;

    // Reset password
    UserEntity findUserByEmail(String email);
    UserEntity findUserByUsername(String username);
    PasswordResetToken createPasswordResetTokenForUser(UserEntity user);
    void sendResetPasswordEmail(UserEntity user, HttpServletRequest request, String appUrl, String token);
    String validatePasswordResetToken(String token);
    Optional<UserEntity> getUserByPasswordResetToken(String passToken);
    void changeUserPassword (UserEntity user, String newPassword);
}

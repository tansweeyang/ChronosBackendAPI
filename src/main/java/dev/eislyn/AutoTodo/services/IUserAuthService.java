package dev.eislyn.AutoTodo.services;

import dev.eislyn.AutoTodo.domain.dto.RegisterRequestDto;
import dev.eislyn.AutoTodo.domain.entities.PasswordResetToken;
import dev.eislyn.AutoTodo.domain.entities.UserEntity;
import dev.eislyn.AutoTodo.domain.entities.VerificationToken;
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
    PasswordResetToken createPasswordResetTokenForUser(UserEntity user);
    void sendResetPasswordEmail(UserEntity user, HttpServletRequest request, String appUrl, String token);
    String validatePasswordResetToken(String token);
    Optional<UserEntity> getUserByPasswordResetToken(String passToken);
    void changeUserPassword (UserEntity user, String newPassword);
}

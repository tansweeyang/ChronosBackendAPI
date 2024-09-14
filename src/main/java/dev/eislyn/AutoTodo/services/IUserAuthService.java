package dev.eislyn.AutoTodo.services;

import dev.eislyn.AutoTodo.domain.dto.RegisterRequestDto;
import dev.eislyn.AutoTodo.domain.entities.PasswordResetToken;
import dev.eislyn.AutoTodo.domain.entities.UserEntity;
import dev.eislyn.AutoTodo.domain.entities.VerificationToken;
import jakarta.servlet.http.HttpServletRequest;

public interface IUserAuthService {
    // Register
    UserEntity registerUser(RegisterRequestDto userDto);
    UserEntity getUser(String verificationToken);
    void saveRegisteredUser(UserEntity user);
    void createVerificationToken(UserEntity user, String token);
    VerificationToken getVerificationToken(String VerificationToken);

    // Reset password
    public UserEntity findUserByEmail(String email);
    public PasswordResetToken createPasswordResetTokenForUser(UserEntity user);
    public void sendResetPasswordEmail(UserEntity user, HttpServletRequest request, String appUrl, String token);
}

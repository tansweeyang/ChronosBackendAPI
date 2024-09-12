package dev.eislyn.AutoTodo.services;

import dev.eislyn.AutoTodo.domain.dto.RegisterRequestDto;
import dev.eislyn.AutoTodo.domain.entities.UserEntity;
import dev.eislyn.AutoTodo.domain.entities.VerificationToken;

public interface IUserRegistrationService {
    UserEntity registerUser(RegisterRequestDto userDto);

    UserEntity getUser(String verificationToken);

    void saveRegisteredUser(UserEntity user);

    void createVerificationToken(UserEntity user, String token);

    VerificationToken getVerificationToken(String VerificationToken);
}

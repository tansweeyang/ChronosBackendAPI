package dev.eislyn.AutoTodo.domain.dto;

import dev.eislyn.AutoTodo.utils.Annotations.ValidPassword;

public class PasswordDto {

    private String oldPassword;

    private  String token;

    @ValidPassword
    private String newPassword;
}


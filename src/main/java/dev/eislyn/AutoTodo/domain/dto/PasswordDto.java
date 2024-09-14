package dev.eislyn.AutoTodo.domain.dto;

import dev.eislyn.AutoTodo.utils.Annotations.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PasswordDto {
    @NotBlank(message = "Token cannot be empty")
    private String token;

    @NotBlank(message = "New password cannot be empty")
    @ValidPassword
    private String newPassword;
}


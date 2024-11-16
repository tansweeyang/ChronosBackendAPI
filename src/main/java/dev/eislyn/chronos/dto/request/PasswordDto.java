package dev.eislyn.chronos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PasswordDto {
    @NotBlank(message = "Token cannot be empty.")
    private String token;

    @NotBlank(message = "New password cannot be empty.")
    @Size(min = 8, message = "Password must be at least 8 characters.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must contain at least one digit, one uppercase, one lowercase letter, one special character, and no whitespace."
    )
    private String newPassword;

    @NotBlank(message = "Confirmation password cannot be empty.")
    private String confirmationPassword;
}


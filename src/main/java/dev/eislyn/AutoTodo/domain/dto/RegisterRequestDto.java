package dev.eislyn.AutoTodo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDto {
    private String email;
    private String username;
    private String password;
    private String confirmationPassword;
}

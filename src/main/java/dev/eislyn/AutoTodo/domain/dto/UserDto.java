package dev.eislyn.AutoTodo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String userId;
    private Duration totalAvailableTime;
    private Duration maxDuration;
    private float c1;
    private float c2;
    private float c3;
}

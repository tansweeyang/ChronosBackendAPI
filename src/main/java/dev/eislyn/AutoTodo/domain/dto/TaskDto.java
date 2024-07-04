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
public class TaskDto {
    private String taskId;
    private String taskName;
    private int effort;
    private int enjoyability;
    private Duration duration;
}

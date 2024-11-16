package dev.eislyn.AutoTodo.domain.dto;

import dev.eislyn.AutoTodo.domain.enums.TaskColor;
import dev.eislyn.AutoTodo.domain.enums.TaskType;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTaskDto {
    private String taskId;
    private String taskName;
    private int effort;
    private int enjoyability;
    private TaskType type;
    private TaskColor color;
    private Duration duration;
    private boolean archived;
    private LocalDate dueDate;
}

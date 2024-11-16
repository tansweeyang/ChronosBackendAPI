package dev.eislyn.chronos.dto;

import dev.eislyn.chronos.model.enums.TaskColor;
import dev.eislyn.chronos.model.enums.TaskType;
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

package dev.eislyn.AutoTodo.domain.dto;

import dev.eislyn.AutoTodo.domain.entities.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneratedTaskScheduleDto {
    private String generatedTaskId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private TaskEntity task;
}

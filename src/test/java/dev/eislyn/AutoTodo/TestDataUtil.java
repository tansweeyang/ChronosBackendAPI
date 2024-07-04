package dev.eislyn.AutoTodo;

import dev.eislyn.AutoTodo.domain.dto.TaskDto;
import dev.eislyn.AutoTodo.domain.entities.TaskEntity;

import java.time.Duration;
import java.time.LocalDateTime;

public final class TestDataUtil {
    private TestDataUtil(){}

    public static TaskEntity createTestTaskEntityA() {
        return TaskEntity.builder()
                .taskId(null)
                .taskName("Task A")
                .effort(10)
                .enjoyability(3)
                .duration(Duration.ofHours(1))
                .build();
    }

    public static TaskDto createTestTaskDtoA() {
        return TaskDto.builder()
                .taskId(null)
                .taskName("Task A")
                .effort(10)
                .enjoyability(3)
                .duration(Duration.ofHours(1))
                .build();
    }

    public static TaskEntity createTestTaskB() {
        return TaskEntity.builder()
                .taskId(null)
                .taskName("Task B")
                .effort(2)
                .enjoyability(3)
                .duration(Duration.ofHours(1))
                .build();
    }

    public static TaskEntity createTestTaskC() {
        return TaskEntity.builder()
                .taskId(null)
                .taskName("Task C")
                .effort(5)
                .enjoyability(8)
                .duration(Duration.ofHours(1))
                .build();
    }
}
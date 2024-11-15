package dev.eislyn.AutoTodo;

import dev.eislyn.AutoTodo.domain.dto.TaskDto;
import dev.eislyn.AutoTodo.domain.entities.TaskEntity;
import dev.eislyn.AutoTodo.domain.entities.UserEntity;
import dev.eislyn.AutoTodo.domain.enums.TaskColor;
import dev.eislyn.AutoTodo.domain.enums.TaskType;

import java.time.Duration;

public final class TestDataUtil {
    private TestDataUtil(){}

    public static TaskEntity createTestTaskEntityA() {
        UserEntity mockUser = UserEntity.builder()
                .id(2L)
                .email("ryantansweeyang@gmail.com")
                .username("ryan")
                .password("Password1*&^123")
                .enabled(true)
                .roles("ROLE_USER")
                .build();

        return TaskEntity.builder()
                .taskId(null)
                .taskName("Task A")
                .effort(10)
                .enjoyability(3)
                .type(TaskType.AUTO)
                .color(TaskColor.RED)
                .duration(Duration.ofHours(1))
                .archived(true)
                .user(mockUser)  // Mock user set here
                .build();
    }

    public static TaskDto createTestTaskDtoA() {
        return TaskDto.builder()
                .taskId(null)
                .taskName("Task A")
                .effort(10)
                .enjoyability(3)
                .type(TaskType.AUTO)
                .color(TaskColor.RED)
                .duration(Duration.ofHours(1))
                .archived(true)
                .build();
    }

    public static TaskEntity createTestTaskB() {
        return TaskEntity.builder()
                .taskId(null)
                .taskName("Task B")
                .effort(2)
                .enjoyability(3)
                .type(TaskType.MANUAL)
                .color(TaskColor.GREEN)
                .duration(Duration.ofHours(1))
                .archived(false)
                .build();
    }

    public static TaskEntity createTestTaskC() {
        return TaskEntity.builder()
                .taskId(null)
                .taskName("Task C")
                .effort(5)
                .enjoyability(8)
                .type(TaskType.AUTO)
                .color(TaskColor.BLUE)
                .duration(Duration.ofHours(1))
                .archived(true)
                .build();
    }
}
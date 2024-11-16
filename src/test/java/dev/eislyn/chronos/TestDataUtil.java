package dev.eislyn.chronos;

import dev.eislyn.chronos.dto.request.UpdateTaskDto;
import dev.eislyn.chronos.model.TaskEntity;
import dev.eislyn.chronos.model.UserEntity;
import dev.eislyn.chronos.model.enums.TaskColor;
import dev.eislyn.chronos.model.enums.TaskType;

import java.time.Duration;
import java.time.LocalDate;

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

    public static UpdateTaskDto createTestTaskDtoA() {
        UserEntity mockUser = UserEntity.builder()
                .id(2L)
                .email("ryantansweeyang@gmail.com")
                .username("ryan")
                .password("Password1*&^123")
                .enabled(true)
                .roles("ROLE_USER")
                .build();

        return UpdateTaskDto.builder()
                .taskId(null)
                .taskName("Task A")
                .effort(10)
                .enjoyability(3)
                .type(TaskType.AUTO)
                .color(TaskColor.RED)
                .duration(Duration.ofHours(1))
                .archived(true)
                .dueDate(LocalDate.now())
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
package dev.eislyn.chronos;

import dev.eislyn.chronos.dto.request.UpdateTaskDto;
import dev.eislyn.chronos.model.Task;
import dev.eislyn.chronos.model.User;
import dev.eislyn.chronos.model.enums.TaskColor;
import dev.eislyn.chronos.model.enums.TaskType;

import java.time.Duration;
import java.time.LocalDate;

public final class TestDataUtil {
    private TestDataUtil(){}

    public static Task createTestTaskEntityA() {
        User mockUser = User.builder()
                .id(2L)
                .email("ryantansweeyang@gmail.com")
                .username("ryan")
                .password("Password1*&^123")
                .enabled(true)
                .roles("ROLE_USER")
                .build();

        return Task.builder()
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
        User mockUser = User.builder()
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

    public static Task createTestTaskB() {
        return Task.builder()
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

    public static Task createTestTaskC() {
        return Task.builder()
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
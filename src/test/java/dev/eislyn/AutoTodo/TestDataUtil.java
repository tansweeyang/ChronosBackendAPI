package dev.eislyn.AutoTodo;

import dev.eislyn.AutoTodo.domain.entities.GeneratedTaskScheduleEntity;
import dev.eislyn.AutoTodo.domain.entities.Reminder;
import dev.eislyn.AutoTodo.domain.entities.TaskEntity;

import java.time.LocalDateTime;

public final class TestDataUtil {
    private TestDataUtil(){}

    public static TaskEntity createTestTaskA() {
        return TaskEntity.builder()
                .taskId(null)
                .taskName("Task A")
                .effort(10)
                .enjoyability(3)
                .note("Note")
                .build();
    }

    public static TaskEntity createTestTaskB() {
        return TaskEntity.builder()
                .taskId(null)
                .taskName("Task B")
                .effort(2)
                .enjoyability(3)
                .note("Note")
                .build();
    }

    public static TaskEntity createTestTaskC() {
        return TaskEntity.builder()
                .taskId(null)
                .taskName("Task C")
                .effort(5)
                .enjoyability(8)
                .note("Note")
                .build();
    }

    public static Reminder createTestReminderA() {
        return Reminder.builder()
                .id(1L)
                .reminderName("Reminder A")
                .dateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .note("Note")
                .build();
    }

    public static Reminder createTestReminderB() {
        return Reminder.builder()
                .id(2L)
                .reminderName("Reminder B")
                .dateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .note("Note")
                .build();
    }

    public static Reminder createTestReminderC() {
        return Reminder.builder()
                .id(3L)
                .reminderName("Reminder C")
                .dateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .note("Note")
                .build();
    }

    public static GeneratedTaskScheduleEntity createTestGeneratedTaskScheduleA() {
        TaskEntity taskA = createTestTaskA();

        return GeneratedTaskScheduleEntity.builder()
                .generatedTaskId(null)
                .startDateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .endDateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .task(taskA)
                .build();
    }

    public static GeneratedTaskScheduleEntity createTestGeneratedTaskScheduleB() {
        TaskEntity taskB = createTestTaskB();

        return GeneratedTaskScheduleEntity.builder()
                .generatedTaskId(null)
                .startDateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .endDateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .task(taskB)
                .build();
    }

    public static GeneratedTaskScheduleEntity createTestGeneratedTaskScheduleC() {
        TaskEntity taskC = createTestTaskC();

        return GeneratedTaskScheduleEntity.builder()
                .generatedTaskId(null)
                .startDateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .endDateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .task(taskC)
                .build();
    }
}
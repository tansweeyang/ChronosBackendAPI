package dev.eislyn.AutoTodo;

import dev.eislyn.AutoTodo.domain.dto.EventDto;
import dev.eislyn.AutoTodo.domain.dto.GeneratedTaskScheduleDto;
import dev.eislyn.AutoTodo.domain.dto.ReminderDto;
import dev.eislyn.AutoTodo.domain.dto.TaskDto;
import dev.eislyn.AutoTodo.domain.entities.EventEntity;
import dev.eislyn.AutoTodo.domain.entities.GeneratedTaskScheduleEntity;
import dev.eislyn.AutoTodo.domain.entities.ReminderEntity;
import dev.eislyn.AutoTodo.domain.entities.TaskEntity;

import java.time.LocalDateTime;

public final class TestDataUtil {
    private TestDataUtil(){}

    public static TaskEntity createTestTaskEntityA() {
        return TaskEntity.builder()
                .taskId(null)
                .taskName("Task A")
                .effort(10)
                .enjoyability(3)
                .note("Note")
                .build();
    }

    public static TaskDto createTestTaskDtoA() {
        return TaskDto.builder()
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

    public static ReminderEntity createTestReminderEntityA() {
        return ReminderEntity.builder()
                .reminderId(null)
                .reminderName("Reminder A")
                .dateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .note("Note")
                .build();
    }

    public static ReminderDto createTestReminderDtoA() {
        return ReminderDto.builder()
                .reminderId(null)
                .reminderName("Reminder A")
                .dateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .note("Note")
                .build();
    }

    public static ReminderEntity createTestReminderB() {
        return ReminderEntity.builder()
                .reminderId(null)
                .reminderName("Reminder B")
                .dateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .note("Note")
                .build();
    }

    public static ReminderEntity createTestReminderC() {
        return ReminderEntity.builder()
                .reminderId(null)
                .reminderName("Reminder C")
                .dateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .note("Note")
                .build();
    }

    public static GeneratedTaskScheduleEntity createTestGeneratedTaskScheduleEntityA() {
        TaskEntity taskA = createTestTaskEntityA();

        return GeneratedTaskScheduleEntity.builder()
                .generatedTaskId(null)
                .startDateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .endDateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .task(taskA)
                .build();
    }

    public static GeneratedTaskScheduleDto createTestGeneratedTaskScheduleDtoA() {
        TaskEntity taskA = createTestTaskEntityA();

        return GeneratedTaskScheduleDto.builder()
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

    public static EventEntity createTestEventEntityA() {
        return EventEntity.builder()
                .eventId(null)
                .eventName("Event A")
                .startDateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .endDateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .note("Note")
                .build();
    }

    public static EventDto createTestEventDtoA() {
        return EventDto.builder()
                .eventId(null)
                .eventName("Event A")
                .startDateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .endDateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .note("Note")
                .build();
    }

    public static EventEntity createTestEventB() {
        return EventEntity.builder()
                .eventId(null)
                .eventName("Event B")
                .startDateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .endDateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .note("Note")
                .build();
    }

    public static EventEntity createTestEventC() {
        return EventEntity.builder()
                .eventId(null)
                .eventName("Event C")
                .startDateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .endDateTime(LocalDateTime.of(1970, 1, 1, 0, 0))
                .note("Note")
                .build();
    }
}
package dev.eislyn.chronos_calendar.service;

import dev.eislyn.chronos_calendar.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITaskService {
    Task save(Task task);

    Page<Task> list(Long userId, Pageable pageable);

    Optional<Task> findByIdAndCreatedBy(Long taskId, Long userId);

    void deleteById(Long taskId);

    Page<Task> listTasksDate(Long userId, LocalDate date, Pageable pageable);

    List<Task> listTasksDate(Long userId, LocalDate date);
}

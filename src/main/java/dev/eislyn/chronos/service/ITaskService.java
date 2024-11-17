package dev.eislyn.chronos.service;

import dev.eislyn.chronos.model.Task;
import dev.eislyn.chronos.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITaskService {
    Task save(Task task);

    Iterable<Task> saveAll(Iterable<Task> tasks);

    List<Task> findAll();

    Page<Task> findAll(Pageable pageable);

    Page<Task> findTasksByUser(User user, Pageable pageable);

    Page<Task> findTasksByUserAndDueDateBetween(User user, LocalDate startDate, LocalDate endDate, Pageable pageable);

    Optional<Task> findOne(String taskId);

    boolean isExist(String taskId);

    void delete(String taskId);

    List<Task> findTasksByType(String type);
}

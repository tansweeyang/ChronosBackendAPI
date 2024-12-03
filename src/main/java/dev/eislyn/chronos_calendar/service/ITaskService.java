package dev.eislyn.chronos_calendar.service;

import dev.eislyn.chronos_calendar.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ITaskService {
    Task save(Task task);

    Page<Task> findByCreatedBy(Long userId, Pageable pageable);

    Optional<Task> findByIdAndCreatedBy(Long taskId, Long userId);

    void deleteById(Long taskId);

//    Iterable<Task> saveAll(Iterable<Task> tasks);
//
//    List<Task> findAll();
//
//    Page<Task> findAll(Pageable pageable);
//
//    Page<Task> findTasksByUser(User user, Pageable pageable);
//
//    Page<Task> findTasksByUserAndDueDateBetween(User user, LocalDate startDate, LocalDate endDate, Pageable pageable);
//
//    Optional<Task> findOne(String taskId);
//
//    boolean isExist(String taskId);
//
//    void delete(String taskId);
//
//    List<Task> findTasksByType(String type);
}

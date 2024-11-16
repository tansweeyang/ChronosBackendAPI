package dev.eislyn.chronos.service;

import dev.eislyn.chronos.model.TaskEntity;
import dev.eislyn.chronos.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITaskService {
    TaskEntity save(TaskEntity task);

    Iterable<TaskEntity> saveAll(Iterable<TaskEntity> tasks);

    List<TaskEntity> findAll();

    Page<TaskEntity> findAll(Pageable pageable);

    Page<TaskEntity> findTasksByUser(UserEntity user, Pageable pageable);

    Page<TaskEntity> findTasksByUserAndDueDateBetween(UserEntity user, LocalDate startDate, LocalDate endDate, Pageable pageable);

    Optional<TaskEntity> findOne(String taskId);

    boolean isExist(String taskId);

    void delete(String taskId);

    List<TaskEntity> findTasksByType(String type);
}

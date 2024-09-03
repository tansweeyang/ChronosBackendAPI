package dev.eislyn.AutoTodo.services;

import dev.eislyn.AutoTodo.domain.entities.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.config.Task;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface TaskService {
    TaskEntity save(TaskEntity task);

    Iterable<TaskEntity> saveAll(Iterable<TaskEntity> tasks);

    List<TaskEntity> findAll();

    Page<TaskEntity> findAll(Pageable pageable);

    Optional<TaskEntity> findOne(String taskId);

    boolean isExist(String taskId);

    void delete(String taskId);

    List<TaskEntity> findTasksByType(String type);
}

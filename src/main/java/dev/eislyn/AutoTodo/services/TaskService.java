package dev.eislyn.AutoTodo.services;

import dev.eislyn.AutoTodo.domain.entities.TaskEntity;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    TaskEntity createTask(TaskEntity task);

    List<TaskEntity> findAll();

    Optional<TaskEntity> findOne(String taskId);
}

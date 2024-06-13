package dev.eislyn.AutoTodo.services;

import dev.eislyn.AutoTodo.domain.entities.TaskEntity;

public interface TaskService {
    TaskEntity createTask(TaskEntity task);
}

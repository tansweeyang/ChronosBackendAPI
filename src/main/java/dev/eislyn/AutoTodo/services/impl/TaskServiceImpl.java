package dev.eislyn.AutoTodo.services.impl;

import dev.eislyn.AutoTodo.domain.entities.TaskEntity;
import dev.eislyn.AutoTodo.repositories.TaskRepository;
import dev.eislyn.AutoTodo.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskEntity save(TaskEntity taskEntity) {
        return taskRepository.save(taskEntity);
    }

    @Override
    public List<TaskEntity> findAll() {
        return StreamSupport.stream(taskRepository
                        .findAll()
                        .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TaskEntity> findOne(String taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public boolean isExist(String taskId) {
        return taskRepository.existsById(taskId);
    }

    @Override
    public void delete(String taskId) {
        taskRepository.deleteById(taskId);
    }


}

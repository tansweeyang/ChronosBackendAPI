package dev.eislyn.chronos.service.impl;

import dev.eislyn.chronos.model.TaskEntity;
import dev.eislyn.chronos.model.UserEntity;
import dev.eislyn.chronos.repository.TaskRepository;
import dev.eislyn.chronos.service.ITaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TaskServiceImpl implements ITaskService {
    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskEntity save(TaskEntity taskEntity) {
        return taskRepository.save(taskEntity);
    }

    @Override
    public Iterable<TaskEntity> saveAll(Iterable<TaskEntity> taskEntities) {
        return taskRepository.saveAll(taskEntities);
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
    public Page<TaskEntity> findTasksByUser(UserEntity user, Pageable pageable) {
        return taskRepository.findByUser(user, pageable);
    }

    @Override
    public Page<TaskEntity> findTasksByUserAndDueDateBetween(UserEntity user, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return taskRepository.findByUserAndDueDateBetween(user, startDate, endDate, pageable);
    }

    @Override
    public Page<TaskEntity> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
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

    @Override
    public List<TaskEntity> findTasksByType(String type) {
        return taskRepository.findByType(type);
    }
}

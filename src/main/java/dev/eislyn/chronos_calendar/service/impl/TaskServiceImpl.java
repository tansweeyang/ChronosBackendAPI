package dev.eislyn.chronos_calendar.service.impl;

import dev.eislyn.chronos_calendar.model.Task;
import dev.eislyn.chronos_calendar.repository.TaskRepository;
import dev.eislyn.chronos_calendar.service.ITaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {
    private final TaskRepository taskRepository;

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Page<Task> list(Long userId, Pageable pageable) {
        return taskRepository.findByCreatedBy(userId, pageable);
    }

    @Override
    public Optional<Task> findByIdAndCreatedBy(Long taskId, Long userId) {
        return taskRepository.findByIdAndCreatedBy(taskId, userId);
    }

    @Override
    public void deleteById(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public Page<Task> listTasksDate(Long userId, LocalDate date, Pageable pageable) {
        return taskRepository.findByCreatedByAndStartDate(userId, date, pageable);
    }

    @Override
    public List<Task> listTasksDate(Long userId, LocalDate date) {
        return taskRepository.findByCreatedByAndStartDate(userId, date);
    }
}
package dev.eislyn.chronos_calendar.service.impl;

import dev.eislyn.chronos_calendar.model.Task;
import dev.eislyn.chronos_calendar.repository.TaskRepository;
import dev.eislyn.chronos_calendar.service.ITaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Page<Task> findByCreatedBy(Long userId, Pageable pageable) {
        return taskRepository.findByCreatedBy(userId, pageable);
    }

    @Override
    public Optional<Task> findByIdAndCreatedBy(Long taskId, Long userId) {
        return taskRepository.findByIdAndCreatedBy(taskId, userId);
    }

    public void deleteById(Long taskId) {
        taskRepository.deleteById(taskId);
    }

//    @Override
//    public Iterable<Task> saveAll(Iterable<Task> taskEntities) {
//        return taskRepository.saveAll(taskEntities);
//    }
//
//    @Override
//    public List<Task> findAll() {
//        return StreamSupport.stream(taskRepository
//                        .findAll()
//                        .spliterator(),
//                        false)
//                .collect(Collectors.toList());
//    }
//
////    @Override
////    public Page<Task> findTasksByUser(User user, Pageable pageable) {
////        return taskRepository.findByUser(user, pageable);
////    }
////
////    @Override
////    public Page<Task> findTasksByUserAndDueDateBetween(User user, LocalDate startDate, LocalDate endDate, Pageable pageable) {
////        return taskRepository.findByUserAndDueDateBetween(user, startDate, endDate, pageable);
////    }
//
//    @Override
//    public Page<Task> findAll(Pageable pageable) {
//        return taskRepository.findAll(pageable);
//    }
//
//    @Override
//    public Optional<Task> findOne(String taskId) {
//        return taskRepository.findById(id);
//    }
//
//    @Override
//    public boolean isExist(String taskId) {
//        return taskRepository.existsById(id);
//    }
//
//    @Override
//    public void delete(String taskId) {
//        taskRepository.deleteById(id);
//    }
//
//    @Override
//    public List<Task> findTasksByType(String type) {
//        return taskRepository.findByType(type);
//    }
}

package dev.eislyn.AutoTodo.services;

import dev.eislyn.AutoTodo.domain.entities.GeneratedTaskScheduleEntity;

import java.util.List;
import java.util.Optional;

public interface GeneratedTaskScheduleService {
    GeneratedTaskScheduleEntity save(GeneratedTaskScheduleEntity generatedTaskSchedule);

    List<GeneratedTaskScheduleEntity> findAll();

    Optional<GeneratedTaskScheduleEntity> findOne(String generatedTaskId);
}

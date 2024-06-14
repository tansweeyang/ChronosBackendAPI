package dev.eislyn.AutoTodo.services;

import dev.eislyn.AutoTodo.domain.entities.ReminderEntity;

import java.util.List;
import java.util.Optional;

public interface ReminderService {
    ReminderEntity save(ReminderEntity reminder);

    List<ReminderEntity> findAll();

    Optional<ReminderEntity> findOne(String reminderId);
}

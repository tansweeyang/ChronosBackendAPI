package dev.eislyn.AutoTodo.services.impl;

import dev.eislyn.AutoTodo.domain.entities.ReminderEntity;
import dev.eislyn.AutoTodo.repositories.ReminderRepository;
import dev.eislyn.AutoTodo.services.ReminderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ReminderServiceImpl implements ReminderService {
    private ReminderRepository reminderRepository;

    public ReminderServiceImpl(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    @Override
    public ReminderEntity save(ReminderEntity reminderEntity) {
        return reminderRepository.save(reminderEntity);
    }

    @Override
    public List<ReminderEntity> findAll() {
        return StreamSupport.stream(reminderRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReminderEntity> findOne(String reminderId) {
        return reminderRepository.findById(reminderId);
    }
}

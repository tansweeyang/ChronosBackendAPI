package dev.eislyn.AutoTodo.services.impl;

import dev.eislyn.AutoTodo.domain.entities.GeneratedTaskScheduleEntity;
import dev.eislyn.AutoTodo.repositories.GeneratedTaskScheduleRepository;
import dev.eislyn.AutoTodo.services.GeneratedTaskScheduleService;
import org.springframework.stereotype.Service;

@Service
public class GeneratedTaskScheduleServiceImpl implements GeneratedTaskScheduleService {
    private GeneratedTaskScheduleRepository generatedTaskScheduleRepository;

    public GeneratedTaskScheduleServiceImpl(GeneratedTaskScheduleRepository generatedTaskScheduleRepository) {
        this.generatedTaskScheduleRepository = generatedTaskScheduleRepository;
    }

    @Override
    public GeneratedTaskScheduleEntity createGeneratedTaskSchedule(GeneratedTaskScheduleEntity generatedTaskSchedule) {
        return generatedTaskScheduleRepository.save(generatedTaskSchedule);
    }
}

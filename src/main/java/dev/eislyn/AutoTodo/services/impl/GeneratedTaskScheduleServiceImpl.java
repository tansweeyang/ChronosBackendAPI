package dev.eislyn.AutoTodo.services.impl;

import dev.eislyn.AutoTodo.domain.entities.GeneratedTaskScheduleEntity;
import dev.eislyn.AutoTodo.repositories.GeneratedTaskScheduleRepository;
import dev.eislyn.AutoTodo.services.GeneratedTaskScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GeneratedTaskScheduleServiceImpl implements GeneratedTaskScheduleService {
    private GeneratedTaskScheduleRepository generatedTaskScheduleRepository;

    public GeneratedTaskScheduleServiceImpl(GeneratedTaskScheduleRepository generatedTaskScheduleRepository) {
        this.generatedTaskScheduleRepository = generatedTaskScheduleRepository;
    }

    @Override
    public GeneratedTaskScheduleEntity save(GeneratedTaskScheduleEntity generatedTaskSchedule) {
        return generatedTaskScheduleRepository.save(generatedTaskSchedule);
    }

    @Override
    public List<GeneratedTaskScheduleEntity> findAll() {
        return StreamSupport.stream(generatedTaskScheduleRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    public Optional<GeneratedTaskScheduleEntity> findOne(String generatedTaskId) {
        return generatedTaskScheduleRepository.findById(generatedTaskId);
    }
}

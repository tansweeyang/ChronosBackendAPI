package dev.eislyn.AutoTodo.controllers;

import dev.eislyn.AutoTodo.domain.dto.GeneratedTaskScheduleDto;
import dev.eislyn.AutoTodo.domain.entities.GeneratedTaskScheduleEntity;
import dev.eislyn.AutoTodo.mappers.Mapper;
import dev.eislyn.AutoTodo.services.impl.GeneratedTaskScheduleServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneratedTaskScheduleController {
    private GeneratedTaskScheduleServiceImpl generatedTaskScheduleService;
    private Mapper<GeneratedTaskScheduleEntity, GeneratedTaskScheduleDto> generatedTaskScheduleMapper;

    public GeneratedTaskScheduleController(GeneratedTaskScheduleServiceImpl generatedTaskScheduleService, Mapper<GeneratedTaskScheduleEntity, GeneratedTaskScheduleDto> generatedTaskScheduleMapper) {
        this.generatedTaskScheduleService = generatedTaskScheduleService;
        this.generatedTaskScheduleMapper = generatedTaskScheduleMapper;
    }

    @PostMapping(path = "/tasks/generated")
    public ResponseEntity<GeneratedTaskScheduleDto> createGeneratedTaskSchedule(@RequestBody GeneratedTaskScheduleDto generatedTaskSchedule) {
        GeneratedTaskScheduleEntity generatedTaskScheduleEntity = generatedTaskScheduleMapper.mapFrom(generatedTaskSchedule);
        GeneratedTaskScheduleEntity savedGeneratedTaskScheduleEntity = generatedTaskScheduleService.createGeneratedTaskSchedule(generatedTaskScheduleEntity);
        return new ResponseEntity<>(generatedTaskScheduleMapper.mapTo(generatedTaskScheduleEntity), HttpStatus.CREATED);
    }
}

package dev.eislyn.AutoTodo.controllers;

import dev.eislyn.AutoTodo.domain.dto.GeneratedTaskScheduleDto;
import dev.eislyn.AutoTodo.domain.dto.ReminderDto;
import dev.eislyn.AutoTodo.domain.entities.GeneratedTaskScheduleEntity;
import dev.eislyn.AutoTodo.domain.entities.ReminderEntity;
import dev.eislyn.AutoTodo.mappers.Mapper;
import dev.eislyn.AutoTodo.services.impl.GeneratedTaskScheduleServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping(path = "/tasks/generated")
    public List<GeneratedTaskScheduleDto> listReminders() {
        List<GeneratedTaskScheduleEntity> generatedTaskSchedule = generatedTaskScheduleService.findAll();
        return generatedTaskSchedule.stream()
                .map(generatedTaskScheduleMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/tasks/generated/{generatedTaskId}")
    public ResponseEntity<GeneratedTaskScheduleDto> getGeneratedTaskSchedule(@PathVariable("generatedTaskId") String generatedTaskId) {
        Optional<GeneratedTaskScheduleEntity> foundGeneratedTaskSchedule = generatedTaskScheduleService.findOne(generatedTaskId);
        return foundGeneratedTaskSchedule.map(generatedTaskScheduleEntity -> {
            GeneratedTaskScheduleDto generatedTaskScheduleDto = generatedTaskScheduleMapper.mapTo(generatedTaskScheduleEntity);
            return new ResponseEntity<>(generatedTaskScheduleDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

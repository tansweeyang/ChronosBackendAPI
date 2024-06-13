package dev.eislyn.AutoTodo.controllers;

import dev.eislyn.AutoTodo.domain.dto.ReminderDto;
import dev.eislyn.AutoTodo.domain.dto.TaskDto;
import dev.eislyn.AutoTodo.domain.entities.ReminderEntity;
import dev.eislyn.AutoTodo.domain.entities.TaskEntity;
import dev.eislyn.AutoTodo.mappers.Mapper;
import dev.eislyn.AutoTodo.services.ReminderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ReminderController {
    private ReminderService reminderService;
    private Mapper<ReminderEntity, ReminderDto> reminderMapper;

    public ReminderController(ReminderService reminderService, Mapper<ReminderEntity, ReminderDto> reminderMapper) {
        this.reminderService = reminderService;
        this.reminderMapper = reminderMapper;
    }

    @PostMapping(path = "/reminders")
    public ResponseEntity<ReminderDto> createReminder(@RequestBody ReminderDto reminder) {
        ReminderEntity reminderEntity = reminderMapper.mapFrom(reminder);
        ReminderEntity savedReminderEntity = reminderService.createReminder(reminderEntity);
        return new ResponseEntity<>(reminderMapper.mapTo(savedReminderEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/reminders")
    public List<ReminderDto> listReminders() {
        List<ReminderEntity> reminders = reminderService.findAll();
        return reminders.stream()
                .map(reminderMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/reminders/{reminderId}")
    public ResponseEntity<ReminderDto> getRemider(@PathVariable("reminderId") String reminderId) {
        Optional<ReminderEntity> foundReminder = reminderService.findOne(reminderId);
        return foundReminder.map(reminderEntity -> {
            ReminderDto reminderDto = reminderMapper.mapTo(reminderEntity);
            return new ResponseEntity<>(reminderDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

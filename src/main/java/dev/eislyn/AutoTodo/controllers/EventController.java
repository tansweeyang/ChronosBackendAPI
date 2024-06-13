package dev.eislyn.AutoTodo.controllers;

import dev.eislyn.AutoTodo.domain.dto.EventDto;
import dev.eislyn.AutoTodo.domain.dto.GeneratedTaskScheduleDto;
import dev.eislyn.AutoTodo.domain.dto.ReminderDto;
import dev.eislyn.AutoTodo.domain.entities.EventEntity;
import dev.eislyn.AutoTodo.domain.entities.GeneratedTaskScheduleEntity;
import dev.eislyn.AutoTodo.domain.entities.ReminderEntity;
import dev.eislyn.AutoTodo.mappers.Mapper;
import dev.eislyn.AutoTodo.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class EventController {
    private EventService eventService;
    private Mapper<EventEntity, EventDto> eventMapper;

    public EventController(EventService eventService, Mapper<EventEntity, EventDto> eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @PostMapping(path = "/events")
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto event) {
        EventEntity eventEntity = eventMapper.mapFrom(event);
        EventEntity savedEventEntity = eventService.createEvent(eventEntity);
        return new ResponseEntity<>(eventMapper.mapTo(savedEventEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/events")
    public List<EventDto> listEvents() {
        List<EventEntity> generatedTaskSchedule = eventService.findAll();
        return generatedTaskSchedule.stream()
                .map(eventMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/events/{eventId}")
    public ResponseEntity<EventDto> getEvent(@PathVariable("eventId") String eventId) {
        Optional<EventEntity> foundEvent = eventService.findOne(eventId);
        return foundEvent.map(eventEntity -> {
            EventDto eventDto = eventMapper.mapTo(eventEntity);
            return new ResponseEntity<>(eventDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

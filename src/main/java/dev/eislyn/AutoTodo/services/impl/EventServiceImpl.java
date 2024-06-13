package dev.eislyn.AutoTodo.services.impl;

import dev.eislyn.AutoTodo.domain.entities.EventEntity;
import dev.eislyn.AutoTodo.repositories.EventRepository;
import dev.eislyn.AutoTodo.services.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EventServiceImpl implements EventService {
    private EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public EventEntity createEvent(EventEntity event) {
        return eventRepository.save(event);
    }

    @Override
    public List<EventEntity> findAll() {
        return StreamSupport.stream(eventRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EventEntity> findOne(String eventId) {
        return eventRepository.findById(eventId);
    }
}

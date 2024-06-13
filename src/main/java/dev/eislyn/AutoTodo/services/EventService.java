package dev.eislyn.AutoTodo.services;

import dev.eislyn.AutoTodo.domain.entities.EventEntity;

import java.util.List;
import java.util.Optional;

public interface EventService {
    EventEntity createEvent(EventEntity event);

    List<EventEntity> findAll();

    Optional<EventEntity> findOne(String eventId);
}

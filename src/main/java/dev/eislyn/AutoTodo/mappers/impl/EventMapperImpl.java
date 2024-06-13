package dev.eislyn.AutoTodo.mappers.impl;

import dev.eislyn.AutoTodo.domain.dto.EventDto;
import dev.eislyn.AutoTodo.domain.dto.ReminderDto;
import dev.eislyn.AutoTodo.domain.entities.EventEntity;
import dev.eislyn.AutoTodo.domain.entities.ReminderEntity;
import dev.eislyn.AutoTodo.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EventMapperImpl implements Mapper<EventEntity, EventDto> {
    private ModelMapper modelMapper;

    public EventMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public EventDto mapTo(EventEntity eventEntity) {
        return modelMapper.map(eventEntity, EventDto.class);
    }

    @Override
    public EventEntity mapFrom(EventDto eventDto) {
        return modelMapper.map(eventDto, EventEntity.class);
    }
}

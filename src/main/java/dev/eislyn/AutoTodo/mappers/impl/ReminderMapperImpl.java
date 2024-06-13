package dev.eislyn.AutoTodo.mappers.impl;

import dev.eislyn.AutoTodo.domain.dto.ReminderDto;
import dev.eislyn.AutoTodo.domain.entities.ReminderEntity;
import dev.eislyn.AutoTodo.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReminderMapperImpl implements Mapper<ReminderEntity, ReminderDto> {
    private ModelMapper modelMapper;

    public ReminderMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ReminderDto mapTo(ReminderEntity reminderEntity) {
        return modelMapper.map(reminderEntity, ReminderDto.class);
    }

    @Override
    public ReminderEntity mapFrom(ReminderDto reminderDto) {
        return modelMapper.map(reminderDto, ReminderEntity.class);
    }
}

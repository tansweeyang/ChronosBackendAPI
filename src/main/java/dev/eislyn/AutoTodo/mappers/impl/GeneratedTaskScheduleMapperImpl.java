package dev.eislyn.AutoTodo.mappers.impl;

import dev.eislyn.AutoTodo.domain.dto.GeneratedTaskScheduleDto;
import dev.eislyn.AutoTodo.domain.entities.GeneratedTaskScheduleEntity;
import dev.eislyn.AutoTodo.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GeneratedTaskScheduleMapperImpl implements Mapper<GeneratedTaskScheduleEntity, GeneratedTaskScheduleDto> {
    private ModelMapper modelMapper;

    public GeneratedTaskScheduleMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public GeneratedTaskScheduleDto mapTo(GeneratedTaskScheduleEntity generatedTaskScheduleEntity) {
        // .map(source, destination)
        return modelMapper.map(generatedTaskScheduleEntity, GeneratedTaskScheduleDto.class);
    }

    @Override
    public GeneratedTaskScheduleEntity mapFrom(GeneratedTaskScheduleDto generatedTaskScheduleDto) {
        return modelMapper.map(generatedTaskScheduleDto, GeneratedTaskScheduleEntity.class);
    }
}

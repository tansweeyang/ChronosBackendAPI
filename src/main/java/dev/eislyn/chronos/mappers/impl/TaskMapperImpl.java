package dev.eislyn.chronos.mappers.impl;

import dev.eislyn.chronos.model.TaskEntity;
import dev.eislyn.chronos.dto.request.UpdateTaskDto;
import dev.eislyn.chronos.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements Mapper<TaskEntity, UpdateTaskDto> {

    private ModelMapper modelMapper;

    public TaskMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UpdateTaskDto mapTo(TaskEntity taskEntity) {
        return modelMapper.map(taskEntity, UpdateTaskDto.class);
    }

    @Override
    public TaskEntity mapFrom(UpdateTaskDto updateTaskDto) {
        return modelMapper.map(updateTaskDto, TaskEntity.class);
    }
}

package dev.eislyn.AutoTodo.mappers.impl;

import dev.eislyn.AutoTodo.domain.entities.TaskEntity;
import dev.eislyn.AutoTodo.domain.dto.UpdateTaskDto;
import dev.eislyn.AutoTodo.mappers.Mapper;
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

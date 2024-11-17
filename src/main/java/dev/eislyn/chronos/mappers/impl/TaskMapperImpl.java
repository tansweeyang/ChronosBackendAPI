package dev.eislyn.chronos.mappers.impl;

import dev.eislyn.chronos.model.Task;
import dev.eislyn.chronos.dto.request.UpdateTaskDto;
import dev.eislyn.chronos.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements Mapper<Task, UpdateTaskDto> {

    private ModelMapper modelMapper;

    public TaskMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UpdateTaskDto mapTo(Task task) {
        return modelMapper.map(task, UpdateTaskDto.class);
    }

    @Override
    public Task mapFrom(UpdateTaskDto updateTaskDto) {
        return modelMapper.map(updateTaskDto, Task.class);
    }
}

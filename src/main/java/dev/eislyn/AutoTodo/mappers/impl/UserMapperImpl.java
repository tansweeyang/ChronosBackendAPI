package dev.eislyn.AutoTodo.mappers.impl;

import dev.eislyn.AutoTodo.domain.dto.TaskDto;
import dev.eislyn.AutoTodo.domain.dto.UserDto;
import dev.eislyn.AutoTodo.domain.entities.TaskEntity;
import dev.eislyn.AutoTodo.domain.entities.UserEntity;
import dev.eislyn.AutoTodo.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements Mapper<UserEntity, UserDto> {
    private ModelMapper modelMapper;

    public UserMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public UserDto mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserEntity mapFrom(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
}

package dev.eislyn.AutoTodo.controllers;

import dev.eislyn.AutoTodo.domain.dto.UserDto;
import dev.eislyn.AutoTodo.domain.entities.UserEntity;
import dev.eislyn.AutoTodo.mappers.Mapper;
import dev.eislyn.AutoTodo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private UserService userService;
    private Mapper<UserEntity, UserDto> userMapper;

    public UserController(UserService userService, Mapper<UserEntity, UserDto> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        UserEntity userEntity = userMapper.mapFrom(user);
        UserEntity savedUserEntity = userService.save(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/users")
    public List<UserDto> listUsers() {
        List<UserEntity> users = userService.findAll();
        return users.stream()
                .map(userMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/users/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") String userId) {
        Optional<UserEntity> foundUser = userService.findOne(userId);
        return foundUser.map(userEntity -> {
            UserDto userDto = userMapper.mapTo(userEntity);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/users/{userId}")
    public ResponseEntity<UserDto> fullUpdateUser(@PathVariable("userId") String userId, @RequestBody UserDto userDto) {
        if (!userService.isExist(userId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userDto.setUserId(userId);
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity savedUserEntity = userService.save(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/users/{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") String userId) {
        userService.delete(userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

package dev.eislyn.AutoTodo.services;

import dev.eislyn.AutoTodo.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserEntity save(UserEntity user);

    List<UserEntity> findAll();

    Optional<UserEntity> findOne(String userId);

    boolean isExist(String userId);

    void delete(String userId);
}

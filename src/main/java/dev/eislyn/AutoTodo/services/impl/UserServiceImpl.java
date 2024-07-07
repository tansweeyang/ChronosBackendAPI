package dev.eislyn.AutoTodo.services.impl;

import dev.eislyn.AutoTodo.domain.entities.UserEntity;
import dev.eislyn.AutoTodo.repositories.UserRepository;
import dev.eislyn.AutoTodo.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public List<UserEntity> findAll() {
        return StreamSupport.stream(userRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserEntity> findOne(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public boolean isExist(String userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public void delete(String userId) {
        userRepository.deleteById(userId);
    }
}

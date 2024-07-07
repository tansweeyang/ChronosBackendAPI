package dev.eislyn.AutoTodo.repositories;

import dev.eislyn.AutoTodo.domain.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
}

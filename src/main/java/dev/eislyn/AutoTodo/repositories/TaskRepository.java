package dev.eislyn.AutoTodo.repositories;

import dev.eislyn.AutoTodo.domain.entities.TaskEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, String> {
}

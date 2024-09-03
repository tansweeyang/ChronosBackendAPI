package dev.eislyn.AutoTodo.repositories;

import dev.eislyn.AutoTodo.domain.entities.TaskEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, String>, PagingAndSortingRepository<TaskEntity, String> {
    List<TaskEntity> findByType(String type);
}

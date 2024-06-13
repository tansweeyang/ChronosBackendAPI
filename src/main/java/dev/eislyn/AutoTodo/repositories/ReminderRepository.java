package dev.eislyn.AutoTodo.repositories;

import dev.eislyn.AutoTodo.domain.entities.ReminderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReminderRepository extends CrudRepository<ReminderEntity, String> {
}

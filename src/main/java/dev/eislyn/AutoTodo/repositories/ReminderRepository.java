package dev.eislyn.AutoTodo.repositories;

import dev.eislyn.AutoTodo.domain.entities.Reminder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReminderRepository extends CrudRepository<Reminder, Long> {
}

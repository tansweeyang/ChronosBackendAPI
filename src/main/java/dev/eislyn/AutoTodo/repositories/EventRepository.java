package dev.eislyn.AutoTodo.repositories;

import dev.eislyn.AutoTodo.domain.entities.EventEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<EventEntity, String> {
}

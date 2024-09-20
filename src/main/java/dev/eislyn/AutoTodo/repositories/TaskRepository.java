package dev.eislyn.AutoTodo.repositories;

import dev.eislyn.AutoTodo.domain.entities.TaskEntity;
import dev.eislyn.AutoTodo.domain.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, String> {
    List<TaskEntity> findByType(String type);
    Page<TaskEntity> findByUserAndDueDateBetween(UserEntity user, LocalDate startDate, LocalDate endDate, Pageable pageable);
    Page<TaskEntity> findByUser(UserEntity user, Pageable pageable);
}

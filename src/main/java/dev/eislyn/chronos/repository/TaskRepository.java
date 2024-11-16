package dev.eislyn.chronos.repository;

import dev.eislyn.chronos.model.TaskEntity;
import dev.eislyn.chronos.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, String> {
    List<TaskEntity> findByType(String type);
    Page<TaskEntity> findByUserAndDueDateBetween(UserEntity user, LocalDate startDate, LocalDate endDate, Pageable pageable);
    Page<TaskEntity> findByUser(UserEntity user, Pageable pageable);
}

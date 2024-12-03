package dev.eislyn.chronos_calendar.repository;

import dev.eislyn.chronos_calendar.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
      Page<Task> findByCreatedBy(Long userId, Pageable pageable);
      Optional<Task> findByIdAndCreatedBy(Long taskId, Long userId);

//    List<Task> findByType(String type);
//    Page<Task> findByUserAndDueDateBetween(User user, LocalDate startDate, LocalDate endDate, Pageable pageable);
//    Page<Task> findByUser(User user, Pageable pageable);
}
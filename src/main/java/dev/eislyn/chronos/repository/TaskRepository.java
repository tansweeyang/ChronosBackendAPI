package dev.eislyn.chronos.repository;

import dev.eislyn.chronos.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
//    List<Task> findByType(String type);
//    Page<Task> findByUserAndDueDateBetween(User user, LocalDate startDate, LocalDate endDate, Pageable pageable);
//    Page<Task> findByUser(User user, Pageable pageable);
}
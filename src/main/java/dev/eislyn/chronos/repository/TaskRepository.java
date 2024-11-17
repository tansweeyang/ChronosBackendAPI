package dev.eislyn.chronos.repository;

import dev.eislyn.chronos.model.Task;
import dev.eislyn.chronos.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByType(String type);
    Page<Task> findByUserAndDueDateBetween(User user, LocalDate startDate, LocalDate endDate, Pageable pageable);
    Page<Task> findByUser(User user, Pageable pageable);
}

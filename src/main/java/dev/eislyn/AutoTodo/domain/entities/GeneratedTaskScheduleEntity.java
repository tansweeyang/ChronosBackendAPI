package dev.eislyn.AutoTodo.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "generatedTasksSchedule")
public class GeneratedTaskScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Generated if id is not provided
    private String generatedTaskId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    // One task can own many generated task schedules.
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "taskId")
    private TaskEntity task;
}

package dev.eislyn.AutoTodo.domain.entities;

import dev.eislyn.AutoTodo.domain.enums.TaskColor;
import dev.eislyn.AutoTodo.domain.enums.TaskType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String taskId;
    private String taskName;
    private int effort;
    private int enjoyability;

    @Enumerated(EnumType.STRING)  // This is needed to specify how the enum is stored in the database
    private TaskType type;

    @Enumerated(EnumType.STRING)  // This is needed to specify how the enum is stored in the database
    private TaskColor color;

    private Duration duration;
    private boolean archived;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private UserEntity user;

    // Use LocalDate for dueDate
    private LocalDate dueDate;
}

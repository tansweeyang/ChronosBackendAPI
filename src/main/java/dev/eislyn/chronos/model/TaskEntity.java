package dev.eislyn.chronos.model;

import dev.eislyn.chronos.model.enums.TaskColor;
import dev.eislyn.chronos.model.enums.TaskType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;

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
    @JoinColumn(name = "id")
    private UserEntity user;

    // Use LocalDate for dueDate
    private LocalDate dueDate;
}

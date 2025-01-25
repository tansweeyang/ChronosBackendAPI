package dev.eislyn.chronos_calendar.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class Task extends Model {
    private String title;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")  // Specify the format for LocalDate
    private LocalDate startDate;

    @JsonFormat(pattern = "HH:mm")  // Specify the format for LocalTime
    private LocalTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")  // Specify the format for LocalDate
    private LocalDate endDate;

    @JsonFormat(pattern = "HH:mm")  // Specify the format for LocalTime
    private LocalTime endTime;
    private String type;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer effort;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer enjoyability;

    private String colorCode;

    private Boolean archived;
}

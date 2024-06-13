package dev.eislyn.AutoTodo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReminderDto {
    private String reminderId;
    private String reminderName;
    private LocalDateTime dateTime;
    private String note;
}

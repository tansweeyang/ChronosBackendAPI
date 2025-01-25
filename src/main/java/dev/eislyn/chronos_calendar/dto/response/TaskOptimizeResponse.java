package dev.eislyn.chronos_calendar.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record TaskOptimizeResponse(
        Long id,
        String title,
        String description,
        LocalDate startDate,
        LocalTime startTime,
        LocalDate endDate,
        LocalTime endTime,
        String type,
        Integer effort,
        Integer enjoyability,
        String colorCode,
        Boolean archived,
        Long createdBy,
        LocalDateTime createdDate,
        Long lastModifiedBy,
        LocalDateTime lastModifiedDate
) {
}

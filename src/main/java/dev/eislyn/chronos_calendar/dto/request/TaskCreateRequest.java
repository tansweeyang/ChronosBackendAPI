package dev.eislyn.chronos_calendar.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record TaskCreateRequest(
        String title,
        String description,
        LocalDate startDate,
        LocalTime startTime,
        LocalDate endDate,
        LocalTime endTime,
        Integer effort,
        Integer enjoyability,
        String colorCode,
        boolean archived
) {
}
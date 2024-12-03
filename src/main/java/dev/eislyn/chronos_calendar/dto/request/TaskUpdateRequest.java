package dev.eislyn.chronos_calendar.dto.request;

import java.time.LocalDateTime;

public record TaskUpdateRequest(
        String title,
        String description,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String colorCode,
        Boolean archived
) {
}

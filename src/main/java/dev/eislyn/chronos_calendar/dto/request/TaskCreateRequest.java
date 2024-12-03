package dev.eislyn.chronos_calendar.dto.request;

import java.time.LocalDateTime;

public record TaskCreateRequest(
        String id,
        String title,
        String description,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String colorCode,
        boolean archived
) {
}

package dev.eislyn.chronos_calendar.dto.response;

import java.time.LocalDateTime;

public record TaskGetResponse(
        Long id,
        String title,
        String description,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String colorCode,
        boolean archived,
        long createdBy,
        long createdDate,
        long lastModifiedBy,
        long lastModifiedDate
) {
}

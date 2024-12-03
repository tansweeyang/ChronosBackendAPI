package dev.eislyn.chronos_calendar.api.converter.input;

import dev.eislyn.chronos_calendar.dto.request.TaskCreateRequest;
import dev.eislyn.chronos_calendar.dto.request.TaskUpdateRequest;
import dev.eislyn.chronos_calendar.model.Task;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", implementationName = "TaskApiInputConverterImpl")
public interface TaskApiInputConverter {
    Task taskCreateRequest2Task(TaskCreateRequest request);
    Task taskUpdateRequest2Task(TaskUpdateRequest request);
}

package dev.eislyn.chronos_calendar.api.converter.output;

import dev.eislyn.chronos_calendar.dto.response.TaskCreateResponse;
import dev.eislyn.chronos_calendar.dto.response.TaskDeleteResponse;
import dev.eislyn.chronos_calendar.dto.response.TaskGetResponse;
import dev.eislyn.chronos_calendar.dto.response.TaskUpdateResponse;
import dev.eislyn.chronos_calendar.model.Task;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", implementationName = "TaskApiOutputConverterImpl")
public interface TaskApiOutputConverter {
    TaskCreateResponse task2TaskCreateResponse(Task task);
    TaskGetResponse task2TaskGetResponse(Task task);
    TaskUpdateResponse task2TaskUpdateResponse(Task task);
    TaskDeleteResponse task2TaskDeleteResponse(Task task);
}

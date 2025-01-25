package dev.eislyn.chronos_calendar.api.converter.output;

import dev.eislyn.chronos_calendar.dto.response.*;
import dev.eislyn.chronos_calendar.model.Task;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", implementationName = "TaskApiOutputConverterImpl")
public interface TaskApiOutputConverter {
    TaskCreateResponse task2TaskCreateResponse(Task task);

    TaskGetResponse task2TaskGetResponse(Task task);

    TaskUpdateResponse task2TaskUpdateResponse(Task task);

    TaskDeleteResponse task2TaskDeleteResponse(Task task);

    List<TaskOptimizeResponse> taskList2TaskOptimizeResponseList(List<Task> tasks);
}

package dev.eislyn.chronos.api.converter.input;

import dev.eislyn.chronos.dto.request.TaskCreateRequest;
import dev.eislyn.chronos.model.Task;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", implementationName = "TaskApiInputConverterImpl")
public interface TaskApiInputConverter {
    Task taskCreateRequest2Task(TaskCreateRequest request);
}

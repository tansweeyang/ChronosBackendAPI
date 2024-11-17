package dev.eislyn.chronos.api.facade;

import dev.eislyn.chronos.dto.request.TaskCreateRequest;
import dev.eislyn.chronos.dto.response.TaskCreateResponse;
import dev.eislyn.chronos.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface TaskWriteFacade {
    ResponseEntity<ApiResponse<TaskCreateResponse>> createTask(TaskCreateRequest request);
}

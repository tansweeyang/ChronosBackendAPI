package dev.eislyn.chronos.api.facadeImpl;

import dev.eislyn.chronos.api.annotation.MicroService;
import dev.eislyn.chronos.api.facade.TaskWriteFacade;
import dev.eislyn.chronos.dto.request.TaskCreateRequest;
import dev.eislyn.chronos.dto.response.TaskCreateResponse;
import dev.eislyn.chronos.mappers.Mapper;
import dev.eislyn.chronos.model.ApiResponse;
import dev.eislyn.chronos.model.Task;
import dev.eislyn.chronos.model.User;
import dev.eislyn.chronos.service.ITaskService;
import dev.eislyn.chronos.service.IUserAuthService;
import dev.eislyn.chronos.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TaskWriteFacadeImpl implements TaskWriteFacade {
    private IUserAuthService userAuthService;
    private Mapper<Task, TaskCreateRequest> createTaskDtoMapper;
    private Mapper<TaskCreateResponse, Task> createResponseTaskEntityMapper;
    private ITaskService taskService;

    @Override
    @MicroService
    public ResponseEntity<ApiResponse<TaskCreateResponse>> createTask(TaskCreateRequest request) {
        // Get the authenticated user
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("sub");
        User user = userAuthService.findUserByUsername(username);

        Task task = createTaskDtoMapper.mapFrom(request);
        task.setUser(user);
        Task savedTask = taskService.save(task);

        // Generate response
        ApiResponse<TaskCreateResponse> response = new ApiResponse<>("Task created successfully.", createResponseTaskEntityMapper.mapFrom(savedTask));

        return ResponseUtil.buildResponse(response, HttpStatus.CREATED);
    }
}

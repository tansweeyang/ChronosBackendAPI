package dev.eislyn.chronos_calendar.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.eislyn.chronos_calendar.api.converter.input.TaskApiInputConverter;
import dev.eislyn.chronos_calendar.api.converter.output.TaskApiOutputConverter;
import dev.eislyn.chronos_calendar.dto.request.TaskCreateRequest;
import dev.eislyn.chronos_calendar.dto.request.TaskUpdateRequest;
import dev.eislyn.chronos_calendar.dto.response.*;
import dev.eislyn.chronos_calendar.model.GenericResponse;
import dev.eislyn.chronos_calendar.model.Task;
import dev.eislyn.chronos_calendar.service.ITaskService;
import dev.eislyn.chronos_calendar.utils.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final ITaskService taskService;
    private final TaskApiInputConverter taskApiInputConverter;
    private final TaskApiOutputConverter taskApiOutputConverter;

    @PostMapping(path = "/create")
    public ResponseEntity<GenericResponse<TaskCreateResponse>> createTask(@RequestBody TaskCreateRequest request, HttpServletRequest httpServletRequest) {
        // Get logged-in user
        UserResponseDto user = UserUtil.me(httpServletRequest);

        Task task = taskApiInputConverter.taskCreateRequest2Task(request);

        Task createdTask = taskService.save(task);
        TaskCreateResponse response = taskApiOutputConverter.task2TaskCreateResponse(createdTask);

        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>("success", HttpStatus.CREATED.value(), "Task created successfully.", response));
    }

    @GetMapping(path = "/list")
    public ResponseEntity<GenericResponse<Page<Task>>> listUserTasks(Pageable pageable, HttpServletRequest httpServletRequest) {
        // Get logged-in user
        UserResponseDto user = UserUtil.me(httpServletRequest);

        // Fetch all tasks created by the user
        Page<Task> response = taskService.findByCreatedBy(user.getId(), pageable);

        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse("success", HttpStatus.OK.value(), "Tasks retrieved successfully.", response));
    }

    @GetMapping(path = "/{taskId}")
    public ResponseEntity<GenericResponse<TaskGetResponse>> getTask(@PathVariable("taskId") Long taskId, HttpServletRequest httpServletRequest) {
        // Get logged-in user
        UserResponseDto user = UserUtil.me(httpServletRequest);

        Optional<Task> foundTask = taskService.findByIdAndCreatedBy(taskId, user.getId());

        if (foundTask.isPresent()) {
            TaskGetResponse response = taskApiOutputConverter.task2TaskGetResponse(foundTask.get());
            return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse("success", HttpStatus.OK.value(), "Task retrieved successfully.", response));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse("success", HttpStatus.NOT_FOUND.value(), "Tasks retrieved successfully.", null));
        }
    }

    @PutMapping(path = "/{taskId}")
    public ResponseEntity<GenericResponse<TaskUpdateResponse>> updateTask(
            @PathVariable("taskId") Long taskId,
            @RequestBody TaskUpdateRequest request,
            HttpServletRequest httpServletRequest) {
        // Get logged-in user
        UserResponseDto user = UserUtil.me(httpServletRequest);

        // Check if the task exist and belongs to the user
        Optional<Task> existingTask = taskService.findByIdAndCreatedBy(taskId, user.getId());

        // If ok
        if (existingTask.isPresent()) {
            Task taskToUpdate = taskApiInputConverter.taskUpdateRequest2Task(request); // Convert request to updated task object
            taskToUpdate.setId(taskId);

            Task updatedTask = taskService.save(taskToUpdate); // Save updated task

            TaskUpdateResponse response = taskApiOutputConverter.task2TaskUpdateResponse(updatedTask);
            return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>("success", HttpStatus.OK.value(), "Task updated successfully.", response));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse<>("error", HttpStatus.NOT_FOUND.value(), "Task not found.", null));
        }
    }

    @DeleteMapping(path = "/{taskId}")
    public ResponseEntity<GenericResponse<TaskDeleteResponse>> deleteTask(
            @PathVariable("taskId") Long taskId,
            HttpServletRequest httpServletRequest) {
        // Get logged-in user
        UserResponseDto user = UserUtil.me(httpServletRequest);

        // Fetch the task created by the user
        Optional<Task> existingTask = taskService.findByIdAndCreatedBy(taskId, user.getId());

        if (existingTask.isPresent()) {
            taskService.deleteById(taskId); // Delete the task
            TaskDeleteResponse response = taskApiOutputConverter.task2TaskDeleteResponse(existingTask.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new GenericResponse<>("success", HttpStatus.NO_CONTENT.value(), "Task deleted successfully.", response));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse<>("failure", HttpStatus.NOT_FOUND.value(), "Task not found.", null));
        }
    }

    // Helper method to log JSON
    private void logJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(object);
            log.info("Sending JSON: {}", json);
        } catch (JsonProcessingException e) {
            log.error("Error converting object to JSON: {}", e.getMessage());
        }
    }
}

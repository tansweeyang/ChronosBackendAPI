package dev.eislyn.chronos_calendar.controller;

import dev.eislyn.chronos_calendar.api.converter.input.TaskApiInputConverter;
import dev.eislyn.chronos_calendar.api.converter.output.TaskApiOutputConverter;
import dev.eislyn.chronos_calendar.dto.request.TaskCreateRequest;
import dev.eislyn.chronos_calendar.dto.request.TaskUpdateRequest;
import dev.eislyn.chronos_calendar.dto.response.*;
import dev.eislyn.chronos_calendar.model.GenericResponse;
import dev.eislyn.chronos_calendar.model.Task;
import dev.eislyn.chronos_calendar.model.enums.TaskType;
import dev.eislyn.chronos_calendar.service.ITaskProcessingService;
import dev.eislyn.chronos_calendar.service.ITaskService;
import dev.eislyn.chronos_calendar.service.IUserReadDomainService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final ITaskService taskService;
    private final TaskApiInputConverter taskApiInputConverter;
    private final TaskApiOutputConverter taskApiOutputConverter;
    private final ITaskProcessingService taskProcessingService;
    private final IUserReadDomainService userReadDomainService;

    @PostMapping(path = "/create")
    public ResponseEntity<GenericResponse<TaskCreateResponse>> createTask(
            @RequestParam String type,
            @RequestBody TaskCreateRequest request,
            HttpServletRequest httpServletRequest
    ) {
        // Get logged-in user
        UserResponseDto user = userReadDomainService.me(httpServletRequest);

        // Create task
        Task task = taskApiInputConverter.taskCreateRequest2Task(request);
        if (type.equalsIgnoreCase("auto")) {
            task.setType(String.valueOf(TaskType.AUTO.getCode()));
        } else if (type.equalsIgnoreCase("manual")) {
            task.setType(String.valueOf(TaskType.MANUAL.getCode()));
        }
        task.setCreatedBy(user.getId());
        task.setLastModifiedBy(user.getId());

        Task createdTask = taskService.save(task);
        TaskCreateResponse response = taskApiOutputConverter.task2TaskCreateResponse(createdTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>("Success", HttpStatus.CREATED.value(), "Task created successfully.", response));
    }

    @GetMapping(path = "/list")
    public ResponseEntity<GenericResponse<Page<Task>>> listTasks(Pageable pageable, HttpServletRequest httpServletRequest) {
        // Get logged-in user
        UserResponseDto user = userReadDomainService.me(httpServletRequest);

        // Fetch all tasks created by the user
        Page<Task> response = taskService.list(user.getId(), pageable);

        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse("Success", HttpStatus.OK.value(), "Tasks retrieved successfully.", response));
    }

    @GetMapping(path = "/{taskId}")
    public ResponseEntity<GenericResponse<TaskGetResponse>> getTask(@PathVariable("taskId") Long taskId, HttpServletRequest httpServletRequest) {
        // Get logged-in user
        UserResponseDto user = userReadDomainService.me(httpServletRequest);

        Optional<Task> foundTask = taskService.findByIdAndCreatedBy(taskId, user.getId());

        if (foundTask.isPresent()) {
            TaskGetResponse response = taskApiOutputConverter.task2TaskGetResponse(foundTask.get());
            return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse("Success", HttpStatus.OK.value(), "Task retrieved successfully.", response));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse("Success", HttpStatus.NOT_FOUND.value(), "Tasks retrieved successfully.", null));
        }
    }

    @PutMapping(path = "/{taskId}")
    public ResponseEntity<GenericResponse<TaskUpdateResponse>> updateTask(
            @PathVariable("taskId") Long taskId,
            @RequestBody TaskUpdateRequest request,
            HttpServletRequest httpServletRequest) {
        // Get logged-in user
        UserResponseDto user = userReadDomainService.me(httpServletRequest);

        // Check if the task exist and belongs to the user
        Optional<Task> existingTask = taskService.findByIdAndCreatedBy(taskId, user.getId());

        // If ok
        if (existingTask.isPresent()) {
            Task taskToUpdate = taskApiInputConverter.taskUpdateRequest2Task(request); // Convert request to updated task object
            taskToUpdate.setId(taskId);

            Task updatedTask = taskService.save(taskToUpdate); // Save updated task

            TaskUpdateResponse response = taskApiOutputConverter.task2TaskUpdateResponse(updatedTask);
            return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>("Success", HttpStatus.OK.value(), "Task updated successfully.", response));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse<>("Error", HttpStatus.NOT_FOUND.value(), "Task not found.", null));
        }
    }

    @DeleteMapping(path = "/{taskId}")
    public ResponseEntity<GenericResponse<TaskDeleteResponse>> deleteTask(
            @PathVariable("taskId") Long taskId,
            HttpServletRequest httpServletRequest) {
        // Get logged-in user
        UserResponseDto user = userReadDomainService.me(httpServletRequest);

        // Fetch the task created by the user
        Optional<Task> existingTask = taskService.findByIdAndCreatedBy(taskId, user.getId());

        if (existingTask.isPresent()) {
            taskService.deleteById(taskId); // Delete the task
            TaskDeleteResponse response = taskApiOutputConverter.task2TaskDeleteResponse(existingTask.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new GenericResponse<>("Success", HttpStatus.NO_CONTENT.value(), "Task deleted successfully.", response));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse<>("failure", HttpStatus.NOT_FOUND.value(), "Task not found.", null));
        }
    }

    @GetMapping("/listTaskDate")
    public ResponseEntity<GenericResponse<Page<Task>>> listTaskDate(
            Pageable pageable,
            @RequestParam LocalDate date,
            HttpServletRequest httpServletRequest
    ) {
        UserResponseDto user = userReadDomainService.me(httpServletRequest);
        Page<Task> response = taskService.listTasksDate(user.getId(), date, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse("Success", HttpStatus.OK.value(), "Tasks retrieved successfully.", response));
    }

    @PutMapping("/optimizeSchedule")
    public ResponseEntity<GenericResponse<List<TaskOptimizeResponse>>> optimizeTaskSchedule(
            @RequestParam LocalDate date,
            HttpServletRequest httpServletRequest) {
        // Get logged-in user
        UserResponseDto user = userReadDomainService.me(httpServletRequest);

        // Find tasks
        List<Task> tasks = taskService.listTasksDate(user.getId(), date);

        if (tasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericResponse<>("Error", HttpStatus.NOT_FOUND.value(), "No tasks exists for optimization.", null));
        }

        // Optimize task schedule
        List<Task> optimizedTasks = taskProcessingService.processDurations(tasks);

        // Update tasks
        for (Task task: optimizedTasks) {
            taskService.save(task);
        }

        // Generate response
        List<TaskOptimizeResponse> response = taskApiOutputConverter.taskList2TaskOptimizeResponseList(optimizedTasks);
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse<>("Success", HttpStatus.OK.value(), "Task schedule optimized successfully.", response));
    }
}

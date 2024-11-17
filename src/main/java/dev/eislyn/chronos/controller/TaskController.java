package dev.eislyn.chronos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.eislyn.chronos.api.facade.TaskWriteFacade;
import dev.eislyn.chronos.dto.request.TaskCreateRequest;
import dev.eislyn.chronos.dto.request.UpdateTaskDto;
import dev.eislyn.chronos.dto.response.TaskCreateResponse;
import dev.eislyn.chronos.model.ApiResponse;
import dev.eislyn.chronos.model.Task;
import dev.eislyn.chronos.model.User;
import dev.eislyn.chronos.mappers.Mapper;
import dev.eislyn.chronos.service.ITaskService;
import dev.eislyn.chronos.service.IUserAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private ITaskService taskService;
    private IUserAuthService userAuthService;
    private Mapper<Task, UpdateTaskDto> updateTaskMapper;
    private TaskWriteFacade taskWriteFacade;

    public TaskController(ITaskService taskService, IUserAuthService userAuthService, Mapper<Task, UpdateTaskDto> updateTaskMapper, TaskWriteFacade taskWriteFacade) {
        this.taskService = taskService;
        this.userAuthService = userAuthService;
        this.updateTaskMapper = updateTaskMapper;
        this.taskWriteFacade = taskWriteFacade;
    }

    @PostMapping(path = "/tasks")
    public ResponseEntity<ApiResponse<TaskCreateResponse>> createTask(@RequestBody TaskCreateRequest request) {
        ResponseEntity<ApiResponse<TaskCreateResponse>> response = taskWriteFacade.createTask(request);
        return response;
    }

    @GetMapping(path = "/tasks")
    public Page<UpdateTaskDto> listTasks(Pageable pageable) {
        // Get logged-in user
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("sub");
        User user = userAuthService.findUserByUsername(username);

        // Fetch all tasks for the user (no date filtering needed)
        Page<Task> tasks = taskService.findTasksByUser(user, pageable);

        // Map tasks to TaskDto and return
        return tasks.map(updateTaskMapper::mapTo);
    }

    @GetMapping(path = "/tasks/{taskId}")
    public ResponseEntity<UpdateTaskDto> getTask(@PathVariable("taskId") String taskId) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("sub");
        User user = userAuthService.findUserByUsername(username);

        Optional<Task> foundTask = taskService.findOne(taskId);

        if (foundTask.isPresent() && foundTask.get().getUser().equals(user)) {
            // The task exists and belongs to the logged-in user
            UpdateTaskDto updateTaskDto = updateTaskMapper.mapTo(foundTask.get());
            return new ResponseEntity<>(updateTaskDto, HttpStatus.OK);
        } else {
            // Task not found or does not belong to the user
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/tasks/{taskId}")
    public ResponseEntity<UpdateTaskDto> fullUpdateTask(@PathVariable("taskId") String taskId, @RequestBody UpdateTaskDto updateTaskDto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("sub");
        User user = userAuthService.findUserByUsername(username);
        Optional<Task> foundTask = taskService.findOne(taskId);
        if (foundTask.isPresent()) {
            Task task = foundTask.get();
            if (!task.getUser().equals(user)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN); // User is not allowed to update this task
            }
            updateTaskDto.setTaskId(taskId);
            Task updatedTask = updateTaskMapper.mapFrom(updateTaskDto);
            updatedTask.setUser(user);
            Task savedTask = taskService.save(updatedTask);
            return new ResponseEntity<>(updateTaskMapper.mapTo(savedTask), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable("taskId") String taskId) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("sub");
        User user = userAuthService.findUserByUsername(username);

        Optional<Task> foundTask = taskService.findOne(taskId);

        if (foundTask.isPresent()) {
            Task task = foundTask.get();
            if (!task.getUser().equals(user)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN); // User is not allowed to delete this task
            }
            taskService.delete(taskId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return 204 No Content on successful deletion
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Task not found
        }
    }

    // Helper method to log JSON
    private void logJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(object);
            logger.info("Sending JSON: {}", json);
        } catch (JsonProcessingException e) {
            logger.error("Error converting object to JSON: {}", e.getMessage());
        }
    }
}

package dev.eislyn.AutoTodo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.eislyn.AutoTodo.domain.dto.TaskDto;
import dev.eislyn.AutoTodo.domain.entities.TaskEntity;
import dev.eislyn.AutoTodo.domain.entities.UserEntity;
import dev.eislyn.AutoTodo.mappers.Mapper;
import dev.eislyn.AutoTodo.services.ITaskService;
import dev.eislyn.AutoTodo.services.IUserAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private ITaskService taskService;
    private IUserAuthService userAuthService;
    private Mapper<TaskEntity, TaskDto> taskMapper;

    public TaskController(ITaskService taskService, IUserAuthService userAuthService,Mapper<TaskEntity, TaskDto> taskMapper) {
        this.taskService = taskService;
        this.userAuthService = userAuthService;
        this.taskMapper = taskMapper;
    }

    @PostMapping(path = "/tasks")
    public ResponseEntity createTask(@RequestBody TaskDto task) {
        // Get the authenticated user
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("sub");
        UserEntity user = userAuthService.findUserByUsername(username);

        TaskEntity taskEntity = taskMapper.mapFrom(task);
        taskEntity.setUser(user);
        TaskEntity savedTaskEntity = taskService.save(taskEntity);
        return new ResponseEntity<>(taskMapper.mapTo(savedTaskEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/tasks")
    public Page<TaskDto> listTasks(Pageable pageable) {
        // Get logged-in user
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("sub");
        UserEntity user = userAuthService.findUserByUsername(username);

        // Fetch all tasks for the user (no date filtering needed)
        Page<TaskEntity> tasks = taskService.findTasksByUser(user, pageable);

        // Map tasks to TaskDto and return
        return tasks.map(taskMapper::mapTo);
    }

    @GetMapping(path = "/tasks/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("taskId") String taskId) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("sub");
        UserEntity user = userAuthService.findUserByUsername(username);

        Optional<TaskEntity> foundTask = taskService.findOne(taskId);

        if (foundTask.isPresent() && foundTask.get().getUser().equals(user)) {
            // The task exists and belongs to the logged-in user
            TaskDto taskDto = taskMapper.mapTo(foundTask.get());
            return new ResponseEntity<>(taskDto, HttpStatus.OK);
        } else {
            // Task not found or does not belong to the user
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/tasks/{taskId}")
    public ResponseEntity<TaskDto> fullUpdateTask(@PathVariable("taskId") String taskId, @RequestBody TaskDto taskDto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("sub");
        UserEntity user = userAuthService.findUserByUsername(username);
        Optional<TaskEntity> foundTask = taskService.findOne(taskId);
        if (foundTask.isPresent()) {
            TaskEntity taskEntity = foundTask.get();
            if (!taskEntity.getUser().equals(user)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN); // User is not allowed to update this task
            }
            taskDto.setTaskId(taskId);
            TaskEntity updatedTaskEntity = taskMapper.mapFrom(taskDto);
            updatedTaskEntity.setUser(user);
            TaskEntity savedTaskEntity = taskService.save(updatedTaskEntity);
            return new ResponseEntity<>(taskMapper.mapTo(savedTaskEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable("taskId") String taskId) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("sub");
        UserEntity user = userAuthService.findUserByUsername(username);

        Optional<TaskEntity> foundTask = taskService.findOne(taskId);

        if (foundTask.isPresent()) {
            TaskEntity taskEntity = foundTask.get();
            if (!taskEntity.getUser().equals(user)) {
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

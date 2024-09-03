package dev.eislyn.AutoTodo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.eislyn.AutoTodo.domain.dto.TaskDto;
import dev.eislyn.AutoTodo.domain.entities.TaskEntity;
import dev.eislyn.AutoTodo.mappers.Mapper;
import dev.eislyn.AutoTodo.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private TaskService taskService;
    private Mapper<TaskEntity, TaskDto> taskMapper;
    private RestTemplate restTemplate;

    public TaskController(TaskService taskService, Mapper<TaskEntity, TaskDto> taskMapper, RestTemplate restTemplate) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.restTemplate = restTemplate;
    }

    @PostMapping(path = "/tasks")
    public ResponseEntity createTask(@RequestBody TaskDto task) {
        TaskEntity taskEntity = taskMapper.mapFrom(task);
        TaskEntity savedTaskEntity = taskService.save(taskEntity);
        return new ResponseEntity<>(taskMapper.mapTo(savedTaskEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/tasks")
    public Page<TaskDto> listTasks(Pageable pageable) {
        Page<TaskEntity> tasks = taskService.findAll(pageable);
        return tasks.map(taskMapper::mapTo);
    }

    @GetMapping(path = "/tasks/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("taskId") String taskId) {
        Optional<TaskEntity> foundTask = taskService.findOne(taskId);
        return foundTask.map(taskEntity -> {
            TaskDto taskDto = taskMapper.mapTo(taskEntity);
            return new ResponseEntity<>(taskDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/tasks/{taskId}")
    public ResponseEntity<TaskDto> fullUpdateTask(@PathVariable("taskId") String taskId, @RequestBody TaskDto taskDto) {
        if (!taskService.isExist(taskId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        taskDto.setTaskId(taskId);
        TaskEntity taskEntity = taskMapper.mapFrom(taskDto);
        TaskEntity savedTaskEntity = taskService.save(taskEntity);
        return new ResponseEntity<>(taskMapper.mapTo(savedTaskEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/tasks/{taskId}")
    public ResponseEntity deleteTask(@PathVariable("taskId") String taskId) {
        taskService.delete(taskId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
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

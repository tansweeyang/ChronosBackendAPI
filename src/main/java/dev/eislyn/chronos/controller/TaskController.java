package dev.eislyn.chronos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.eislyn.chronos.api.converter.input.TaskApiInputConverter;
import dev.eislyn.chronos.dto.request.TaskCreateRequest;
import dev.eislyn.chronos.dto.response.UserResponseDto;
import dev.eislyn.chronos.model.GenericResponse;
import dev.eislyn.chronos.model.Task;
import dev.eislyn.chronos.service.ITaskService;
import dev.eislyn.chronos.utils.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final ITaskService taskService;
    private final TaskApiInputConverter taskApiInputConverter;

    @PostMapping(path = "/create")
    public ResponseEntity<GenericResponse<Long>> createTask(@RequestBody TaskCreateRequest request, HttpServletRequest httpServletRequest) {
        UserResponseDto user = UserUtil.me(httpServletRequest);
        Task task = taskApiInputConverter.taskCreateRequest2Task(request);

        taskService.save(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse<>("success", HttpStatus.CREATED.value(), "Task created successfully.", task.getId()));
    }

//    @GetMapping(path = "/tasks")
//    public Page<UpdateTaskDto> listTasks(Pageable pageable) {
//        // Get logged-in user
//
//
//        // Fetch all tasks for the user (no date filtering needed)
//        Page<Task> tasks = taskService.findTasksByUser(user, pageable);
//
//        // Map tasks to TaskDto and return
//        return tasks.map(updateTaskMapper::mapTo);
//    }
//
//    @GetMapping(path = "/tasks/{taskId}")
//    public ResponseEntity<UpdateTaskDto> getTask(@PathVariable("taskId") String taskId) {
//        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = jwt.getClaimAsString("sub");
//        User user = userAuthService.findUserByUsername(username);
//
//        Optional<Task> foundTask = taskService.findOne(taskId);
//
//        if (foundTask.isPresent() && foundTask.get().getUser().equals(user)) {
//            // The task exists and belongs to the logged-in user
//            UpdateTaskDto updateTaskDto = updateTaskMapper.mapTo(foundTask.get());
//            return new ResponseEntity<>(updateTaskDto, HttpStatus.OK);
//        } else {
//            // Task not found or does not belong to the user
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PutMapping(path = "/tasks/{taskId}")
//    public ResponseEntity<UpdateTaskDto> fullUpdateTask(@PathVariable("taskId") String taskId, @RequestBody UpdateTaskDto updateTaskDto) {
//        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = jwt.getClaimAsString("sub");
//        User user = userAuthService.findUserByUsername(username);
//        Optional<Task> foundTask = taskService.findOne(taskId);
//        if (foundTask.isPresent()) {
//            Task task = foundTask.get();
//            if (!task.getUser().equals(user)) {
//                return new ResponseEntity<>(HttpStatus.FORBIDDEN); // User is not allowed to update this task
//            }
//            updateTaskDto.setTaskId(taskId);
//            Task updatedTask = updateTaskMapper.mapFrom(updateTaskDto);
//            updatedTask.setUser(user);
//            Task savedTask = taskService.save(updatedTask);
//            return new ResponseEntity<>(updateTaskMapper.mapTo(savedTask), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping(path = "/tasks/{taskId}")
//    public ResponseEntity<?> deleteTask(@PathVariable("taskId") String taskId) {
//        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = jwt.getClaimAsString("sub");
//        User user = userAuthService.findUserByUsername(username);
//
//        Optional<Task> foundTask = taskService.findOne(taskId);
//
//        if (foundTask.isPresent()) {
//            Task task = foundTask.get();
//            if (!task.getUser().equals(user)) {
//                return new ResponseEntity<>(HttpStatus.FORBIDDEN); // User is not allowed to delete this task
//            }
//            taskService.delete(taskId);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return 204 No Content on successful deletion
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Task not found
//        }
//    }
//
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

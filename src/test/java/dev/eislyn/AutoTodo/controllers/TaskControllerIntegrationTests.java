package dev.eislyn.AutoTodo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.eislyn.AutoTodo.TestDataUtil;
import dev.eislyn.AutoTodo.domain.dto.TaskDto;
import dev.eislyn.AutoTodo.domain.entities.TaskEntity;
import dev.eislyn.AutoTodo.services.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@AutoConfigureMockMvc
public class TaskControllerIntegrationTests {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private TaskService taskService;

    // Autowired is not required if only one constructor exists, but is required for test classes.
    // Autowired is an annotation that enables dependency injection for Java classes. It allows Spring to
    // automatically inject dependencies into the class, eliminating the need for manual configuration.
    @Autowired
    public TaskControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, TaskService taskService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.taskService = taskService;
    }

    @Test
    public void testThatCreateTaskSuccessfullyReturnedHttp201Created() throws Exception {
        TaskDto testTaskDtoA = TestDataUtil.createTestTaskDtoA();
        String taskJson = objectMapper.writeValueAsString(testTaskDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateTaskSuccessfullyReturnedSavedTask() throws Exception {
        TaskDto testTaskDtoA = TestDataUtil.createTestTaskDtoA();
        String taskJson = objectMapper.writeValueAsString(testTaskDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.taskId").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.taskName").value("Task A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.effort").value(10)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.enjoyability").value(3)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.type").value("AUTO")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.color").value("RED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.duration").value("PT1H")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.archived").value(true)
        );
    }

    @Test
    public void testThatListTasksReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListTasksReturnsListOfTasks() throws Exception {
        TaskEntity testTaskEntityA = TestDataUtil.createTestTaskEntityA();
        taskService.save(testTaskEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].taskId").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].taskName").value("Task A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].effort").value(10)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].enjoyability").value(3)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].type").value("AUTO")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].color").value("RED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].duration").value("PT1H")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].archived").value(true)
        );
    }

    @Test
    public void testThatGetTaskReturnsHttpStatus200WhenTaskExists() throws Exception {
        TaskEntity testTaskEntityA = TestDataUtil.createTestTaskEntityA();
        TaskEntity savedTaskEntityA = taskService.save(testTaskEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/" + savedTaskEntityA.getTaskId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetTaskReturnsHttpStatus404WhenNoTaskExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/550e8400-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetTaskReturnsTaskWhenTaskExists() throws Exception {
        TaskEntity testTaskEntityA = TestDataUtil.createTestTaskEntityA();
        TaskEntity savedTaskEntityA = taskService.save(testTaskEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/" + savedTaskEntityA.getTaskId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.taskId").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.taskName").value("Task A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.effort").value(10)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.enjoyability").value(3)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.type").value("AUTO")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.color").value("RED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.duration").value("PT1H")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.archived").value(true)
        );
    }

    @Test
    public void testThatFullUpdateTaskReturnsHttpStatus404WhenNoTaskExists() throws Exception {
        TaskDto testTaskDtoA = TestDataUtil.createTestTaskDtoA();
        String taskDtoJson = objectMapper.writeValueAsString(testTaskDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/tasks/550e8400-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatUpdateTaskReturnsHttpStatus200WhenTaskExists() throws Exception {
        TaskEntity taskEntity = TestDataUtil.createTestTaskEntityA();
        TaskEntity savedTaskEntity = taskService.save(taskEntity);

        TaskDto testTaskDtoA = TestDataUtil.createTestTaskDtoA();
        String taskDtoJson = objectMapper.writeValueAsString(testTaskDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/tasks/" + savedTaskEntity.getTaskId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateTaskUpdatesExistingTask() throws Exception{
        TaskEntity taskEntity = TestDataUtil.createTestTaskEntityA();
        TaskEntity savedTaskEntity = taskService.save(taskEntity);

        TaskDto testTaskDtoA = TestDataUtil.createTestTaskDtoA();
        testTaskDtoA.setTaskName("Task A1");
        String taskDtoJson = objectMapper.writeValueAsString(testTaskDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/tasks/" + savedTaskEntity.getTaskId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskDtoJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.taskId").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.taskName").value("Task A1")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.effort").value(testTaskDtoA.getEffort())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.enjoyability").value(testTaskDtoA.getEnjoyability())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.type").value("AUTO")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.color").value("RED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.duration").value("PT1H")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.archived").value(true)
        );
    }

    @Test
    public  void testThatDeleteTaskReturnsHttpStatus204ForNonExistingTask() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/tasks/550e8400-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public  void testThatDeleteTaskReturnsHttpStatus204ForExistingTask() throws Exception {
        TaskEntity taskEntity = TestDataUtil.createTestTaskEntityA();
        TaskEntity savedTaskEntity = taskService.save(taskEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/tasks/" + savedTaskEntity.getTaskId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}

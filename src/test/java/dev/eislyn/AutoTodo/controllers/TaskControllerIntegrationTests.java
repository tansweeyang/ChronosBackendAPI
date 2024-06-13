package dev.eislyn.AutoTodo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.eislyn.AutoTodo.TestDataUtil;
import dev.eislyn.AutoTodo.domain.entities.TaskEntity;
import dev.eislyn.AutoTodo.services.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
        TaskEntity testTaskA = TestDataUtil.createTestTaskA();
        String taskJson = objectMapper.writeValueAsString(testTaskA);

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
        TaskEntity testTaskA = TestDataUtil.createTestTaskA();
        String taskJson = objectMapper.writeValueAsString(testTaskA);

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
                MockMvcResultMatchers.jsonPath("$.note").value("Note")
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
        TaskEntity testTaskEntityA = TestDataUtil.createTestTaskA();
        taskService.createTask(testTaskEntityA);

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
                MockMvcResultMatchers.jsonPath("$[0].note").value("Note")
        );
    }

    @Test
    public void testThatGetTaskReturnsHttpStatus200WhenTaskExists() throws Exception {
        TaskEntity testTaskEntityA = TestDataUtil.createTestTaskA();
        TaskEntity savedTaskEntityA = taskService.createTask(testTaskEntityA);

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
        TaskEntity testTaskEntityA = TestDataUtil.createTestTaskA();
        TaskEntity savedTaskEntityA = taskService.createTask(testTaskEntityA);

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
                MockMvcResultMatchers.jsonPath("$.note").value("Note")
        );
    }
}

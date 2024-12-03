package dev.eislyn.chronos_calendar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.eislyn.chronos_calendar.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTests {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private ITaskService taskService;
    private JwtEncoder encoder;
    String token;

    // Autowired is not required if only one constructor exists, but is required for test classes.
    // Autowired is an annotation that enables dependency injection for Java classes. It allows Spring to
    // automatically inject dependencies into the class, eliminating the need for manual configuration.
    @Autowired
    public TaskControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, ITaskService taskService, JwtEncoder encoder) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.taskService = taskService;
        this.encoder = encoder;
    }

//    @Test
//    public void testThatCreateTaskSuccessfullyReturnedHttp201Created() throws Exception {
//        UpdateTaskDto testUpdateTaskDtoA = TestDataUtil.createTestTaskDtoA();
//        String taskJson = objectMapper.writeValueAsString(testUpdateTaskDtoA);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/tasks")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(taskJson)
//                        .header("Authorization", "Bearer " + token)
//        ).andExpect(
//                MockMvcResultMatchers.status().isCreated()
//        );
//    }
//
//    @Test
//    public void testThatCreateTaskSuccessfullyReturnedSavedTask() throws Exception {
//        UpdateTaskDto testUpdateTaskDtoA = TestDataUtil.createTestTaskDtoA();
//        String taskJson = objectMapper.writeValueAsString(testUpdateTaskDtoA);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/tasks")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(taskJson)
//                        .header("Authorization", "Bearer " + token)
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.taskId").isString()
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.taskName").value("Task A")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.effort").value(10)
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.enjoyability").value(3)
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.type").value("AUTO")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.color").value("RED")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.duration").value("PT1H")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.archived").value(true)
//        );
//    }
//
//    @Test
//    public void testThatListTasksReturnsHttpStatus200() throws Exception {
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/tasks")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + token)
//        ).andExpect(
//                MockMvcResultMatchers.status().isOk()
//        );
//    }
//
//    @Test
//    public void testThatListTasksReturnsListOfTasks() throws Exception {
//        Task testTaskA = TestDataUtil.createTestTaskEntityA();
//        taskService.save(testTaskA);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/tasks")
//                        .param("page", "0")
//                        .param("size", "10")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + token)
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.content[0].taskId").isNotEmpty()
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.content[0].taskName").value("Task A")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.content[0].effort").value(10)
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.content[0].enjoyability").value(3)
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.content[0].type").value("AUTO")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.content[0].color").value("RED")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.content[0].duration").value("PT1H")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.content[0].archived").value(true)
//        );
//    }
//
//    @Test
//    public void testThatGetTaskReturnsHttpStatus200WhenTaskExists() throws Exception {
//        Task testTaskA = TestDataUtil.createTestTaskEntityA();
//        Task savedTaskA = taskService.save(testTaskA);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/tasks/" + savedTaskA.getTaskId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + token)
//        ).andExpect(
//                MockMvcResultMatchers.status().isOk()
//        );
//    }
//
//    @Test
//    public void testThatGetTaskReturnsHttpStatus404WhenNoTaskExists() throws Exception {
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/tasks/550e8400-e29b-41d4-a716-446655440000")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + token)
//        ).andExpect(
//                MockMvcResultMatchers.status().isNotFound()
//        );
//    }
//
//    @Test
//    public void testThatGetTaskReturnsTaskWhenTaskExists() throws Exception {
//        Task testTaskA = TestDataUtil.createTestTaskEntityA();
//        Task savedTaskA = taskService.save(testTaskA);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/tasks/" + savedTaskA.getTaskId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + token)
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.taskId").isString()
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.taskName").value("Task A")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.effort").value(10)
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.enjoyability").value(3)
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.type").value("AUTO")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.color").value("RED")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.duration").value("PT1H")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.archived").value(true)
//        );
//    }
//
//    @Test
//    public void testThatFullUpdateTaskReturnsHttpStatus404WhenNoTaskExists() throws Exception {
//        UpdateTaskDto testUpdateTaskDtoA = TestDataUtil.createTestTaskDtoA();
//        String taskDtoJson = objectMapper.writeValueAsString(testUpdateTaskDtoA);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.put("/tasks/550e8400-e29b-41d4-a716-446655440000")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(taskDtoJson)
//                        .header("Authorization", "Bearer " + token)
//        ).andExpect(
//                MockMvcResultMatchers.status().isNotFound()
//        );
//    }
//
//    @Test
//    public void testThatUpdateTaskReturnsHttpStatus200WhenTaskExists() throws Exception {
//        Task task = TestDataUtil.createTestTaskEntityA();
//        Task savedTask = taskService.save(task);
//
//        UpdateTaskDto testUpdateTaskDtoA = TestDataUtil.createTestTaskDtoA();
//        String taskDtoJson = objectMapper.writeValueAsString(testUpdateTaskDtoA);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.put("/tasks/" + savedTask.getTaskId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(taskDtoJson)
//                        .header("Authorization", "Bearer " + token)
//        ).andExpect(
//                MockMvcResultMatchers.status().isOk()
//        );
//    }
//
//    @Test
//    public void testThatFullUpdateTaskUpdatesExistingTask() throws Exception{
//        Task task = TestDataUtil.createTestTaskEntityA();
//        Task savedTask = taskService.save(task);
//
//        UpdateTaskDto testUpdateTaskDtoA = TestDataUtil.createTestTaskDtoA();
//        testUpdateTaskDtoA.setTaskName("Task A1");
//        String taskDtoJson = objectMapper.writeValueAsString(testUpdateTaskDtoA);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.put("/tasks/" + savedTask.getTaskId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(taskDtoJson)
//                        .header("Authorization", "Bearer " + token)
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.taskId").isString()
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.taskName").value("Task A1")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.effort").value(testUpdateTaskDtoA.getEffort())
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.enjoyability").value(testUpdateTaskDtoA.getEnjoyability())
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.type").value("AUTO")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.color").value("RED")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.duration").value("PT1H")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.archived").value(true)
//        );
//    }
//
//    @Test
//    public  void testThatDeleteTaskReturnsHttpStatus404ForNonExistingTask() throws Exception {
//        mockMvc.perform(
//                MockMvcRequestBuilders.delete("/tasks/550e8400-e29b-41d4-a716-446655440000")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + token)
//        ).andExpect(
//                MockMvcResultMatchers.status().isNotFound()
//        );
//    }
//
//    @Test
//    public void testThatDeleteTaskReturnsHttpStatus204ForExistingTask() throws Exception {
//        Task task = TestDataUtil.createTestTaskEntityA();
//        Task savedTask = taskService.save(task);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.delete("/tasks/" + savedTask.getTaskId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("Authorization", "Bearer " + token)
//        ).andExpect(
//                MockMvcResultMatchers.status().isNoContent()
//        );
//    }
}

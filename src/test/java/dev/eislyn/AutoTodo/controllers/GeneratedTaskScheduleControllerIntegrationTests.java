package dev.eislyn.AutoTodo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.eislyn.AutoTodo.TestDataUtil;
import dev.eislyn.AutoTodo.domain.entities.GeneratedTaskScheduleEntity;
import dev.eislyn.AutoTodo.services.GeneratedTaskScheduleService;
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
public class GeneratedTaskScheduleControllerIntegrationTests {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private GeneratedTaskScheduleService generatedTaskScheduleService;

    @Autowired
    public GeneratedTaskScheduleControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, GeneratedTaskScheduleService generatedTaskScheduleService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.generatedTaskScheduleService = generatedTaskScheduleService;
    }

    @Test
    public void testThatCreateGeneratedTaskScheduleSuccessfullyReturnedHttp201Created() throws Exception {
        GeneratedTaskScheduleEntity generatedTaskScheduleEntity = TestDataUtil.createTestGeneratedTaskScheduleEntityA();
        generatedTaskScheduleEntity.setGeneratedTaskId(null);
        String generatedTaskScheduleJson = objectMapper.writeValueAsString(generatedTaskScheduleEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/tasks/generated")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(generatedTaskScheduleJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateGeneratedTaskScheduleSuccessfullyReturnedSavedTask() throws Exception {
        GeneratedTaskScheduleEntity generatedTaskScheduleEntity = TestDataUtil.createTestGeneratedTaskScheduleEntityA();
        generatedTaskScheduleEntity.setGeneratedTaskId(null);
        String generatedTaskScheduleJson = objectMapper.writeValueAsString(generatedTaskScheduleEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/tasks/generated")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(generatedTaskScheduleJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.generatedTaskId").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.startDateTime").value("1970-01-01T00:00:00")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.endDateTime").value("1970-01-01T00:00:00")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.task.taskId").isString() // Validate the task entity
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.task.taskName").value("Task A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.task.effort").value(10)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.task.enjoyability").value(3)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.task.note").value("Note")
        );
    }

    @Test
    public void testThatListGeneratedTaskScheduleReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/generated")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListGeneratedTaskScheduleReturnsListOfGeneratedTaskSchedule() throws Exception {
        GeneratedTaskScheduleEntity testGeneratedTaskScheduleEntityA = TestDataUtil.createTestGeneratedTaskScheduleEntityA();
        generatedTaskScheduleService.save(testGeneratedTaskScheduleEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/generated")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].generatedTaskId").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].startDateTime").value("1970-01-01T00:00:00")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].endDateTime").value("1970-01-01T00:00:00")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].task.taskId").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].task.taskName").value("Task A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].task.effort").value(10)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].task.enjoyability").value(3)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].task.note").value("Note")
        );
    }

    @Test
    public void testThatGetGeneratedTaskScheduleReturnsHttpStatus200WhenGeneratedTaskScheduleExists() throws Exception {
        GeneratedTaskScheduleEntity generatedTaskScheduleEntityA = TestDataUtil.createTestGeneratedTaskScheduleEntityA();
        GeneratedTaskScheduleEntity savedGeneratedTaskScheduleEntityA = generatedTaskScheduleService.save(generatedTaskScheduleEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/generated/" + savedGeneratedTaskScheduleEntityA.getGeneratedTaskId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetGeneratedTaskScheduleReturnsHttpStatus404WhenNoGeneratedTaskScheduleExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/generated/550e8400-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetGeneratedTaskScheduleReturnsGeneratedTaskScheduleWhenGeneratedTaskScheduleExists() throws Exception {
        GeneratedTaskScheduleEntity testGeneratedTaskScheduleEntityA = TestDataUtil.createTestGeneratedTaskScheduleEntityA();
        GeneratedTaskScheduleEntity savedGeneratedTaskScheduleEntityA = generatedTaskScheduleService.save(testGeneratedTaskScheduleEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/generated/" + savedGeneratedTaskScheduleEntityA.getGeneratedTaskId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.generatedTaskId").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.startDateTime").value("1970-01-01T00:00:00")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.endDateTime").value("1970-01-01T00:00:00")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.task.taskId").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.task.taskName").value("Task A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.task.effort").value(10)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.task.enjoyability").value(3)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.task.note").value("Note")
        );
    }
}

package dev.eislyn.AutoTodo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.eislyn.AutoTodo.TestDataUtil;
import dev.eislyn.AutoTodo.domain.entities.GeneratedTaskScheduleEntity;
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

import java.time.LocalDateTime;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class GeneratedTaskScheduleControllerIntegrationTests {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public GeneratedTaskScheduleControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateGeneratedTaskScheduleSuccessfullyReturnedHttp201Created() throws Exception {
        GeneratedTaskScheduleEntity generatedTaskScheduleEntity = TestDataUtil.createTestGeneratedTaskScheduleA();
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
        GeneratedTaskScheduleEntity generatedTaskScheduleEntity = TestDataUtil.createTestGeneratedTaskScheduleA();
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
}

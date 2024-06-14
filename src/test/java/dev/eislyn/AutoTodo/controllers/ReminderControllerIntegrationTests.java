package dev.eislyn.AutoTodo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.eislyn.AutoTodo.TestDataUtil;
import dev.eislyn.AutoTodo.domain.entities.ReminderEntity;
import dev.eislyn.AutoTodo.services.ReminderService;
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
public class ReminderControllerIntegrationTests {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private ReminderService reminderService;

    @Autowired
    public ReminderControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, ReminderService reminderService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.reminderService = reminderService;
    }

    @Test
    public void testThatCreateReminderSuccessfullyReturnedHttp201Created() throws Exception {
        ReminderEntity testReminderEntityA = TestDataUtil.createTestReminderEntityA();
        String reminderJson = objectMapper.writeValueAsString(testReminderEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/reminders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reminderJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateReminderSuccessfullyReturnedSavedReminder() throws Exception {
        ReminderEntity testReminderEntityA = TestDataUtil.createTestReminderEntityA();
        String reminderJson = objectMapper.writeValueAsString(testReminderEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/reminders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reminderJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.reminderId").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.reminderName").value("Reminder A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.dateTime").value("1970-01-01T00:00:00")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.note").value("Note")
        );
    }

    @Test
    public void testThatListRemindersReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/reminders")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListRemindersReturnsListOfReminders() throws Exception {
        ReminderEntity testReminderEntityA = TestDataUtil.createTestReminderEntityA();
        reminderService.save(testReminderEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/reminders")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].reminderId").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].reminderName").value("Reminder A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].dateTime").value("1970-01-01T00:00:00")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].note").value("Note")
        );
    }

    @Test
    public void testThatGetReminderReturnsHttpStatus200WhenReminderExists() throws Exception {
        ReminderEntity testReminderEntityA = TestDataUtil.createTestReminderEntityA();
        ReminderEntity savedReminderEntityA = reminderService.save(testReminderEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/reminders/" + savedReminderEntityA.getReminderId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetReminderReturnsHttpStatus404WhenNoReminderExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/reminders/550e8400-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetReminderReturnsReminderWhenReminderExists() throws Exception {
        ReminderEntity testReminderEntityA = TestDataUtil.createTestReminderEntityA();
        ReminderEntity savedReminderEntityA = reminderService.save(testReminderEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/reminders/" + savedReminderEntityA.getReminderId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.reminderId").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.reminderName").value("Reminder A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.dateTime").value("1970-01-01T00:00:00")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.note").value("Note")
        );
    }
}

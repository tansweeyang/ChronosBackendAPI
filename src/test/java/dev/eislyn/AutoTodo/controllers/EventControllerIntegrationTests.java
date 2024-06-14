package dev.eislyn.AutoTodo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.eislyn.AutoTodo.TestDataUtil;
import dev.eislyn.AutoTodo.domain.entities.EventEntity;
import dev.eislyn.AutoTodo.services.EventService;
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
public class EventControllerIntegrationTests {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private EventService eventService;

    @Autowired
    public EventControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, EventService eventService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.eventService = eventService;
    }

    @Test
    public void testThatCreateEventSuccessfullyReturnedHttp201Created() throws Exception {
        EventEntity testEventEntityA = TestDataUtil.createTestEventEntityA();
        String eventJson = objectMapper.writeValueAsString(testEventEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateReminderSuccessfullyReturnedSavedReminder() throws Exception {
        EventEntity testEventEntityA = TestDataUtil.createTestEventEntityA();
        String eventJson = objectMapper.writeValueAsString(testEventEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.eventId").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.eventName").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.startDateTime").value("1970-01-01T00:00:00")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.endDateTime").value("1970-01-01T00:00:00")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.note").value("Note")
        );
    }

    @Test
    public void testThatListEventsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/events")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListEventsReturnsListOfEvents() throws Exception {
        EventEntity testEventEntityA = TestDataUtil.createTestEventEntityA();
        eventService.save(testEventEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/events")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].eventId").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].eventName").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].startDateTime").value("1970-01-01T00:00:00")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].endDateTime").value("1970-01-01T00:00:00")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].note").value("Note")
        );
    }

    @Test
    public void testThatGetEventReturnsHttpStatus200WhenEventExists() throws Exception {
        EventEntity testEventEntityA = TestDataUtil.createTestEventEntityA();
        EventEntity savedEventEntityA = eventService.save(testEventEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/events/" + savedEventEntityA.getEventId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetEventReturnsHttpStatus404WhenNoEventExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/events/550e8400-e29b-41d4-a716-446655440000")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetEventReturnsReminderWhenEventExists() throws Exception {
        EventEntity testEventEntityA = TestDataUtil.createTestEventEntityA();
        EventEntity savedEventEntityA = eventService.save(testEventEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/events/" + savedEventEntityA.getEventId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.eventId").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.eventName").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.startDateTime").value("1970-01-01T00:00:00")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.endDateTime").value("1970-01-01T00:00:00")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.note").value("Note")
        );
    }
}

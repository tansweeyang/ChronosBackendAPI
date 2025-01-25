package dev.eislyn.chronos_calendar.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.eislyn.chronos_calendar.model.Task;
import dev.eislyn.chronos_calendar.service.ITaskProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskProcessingServiceImpl implements ITaskProcessingService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper; // Jackson ObjectMapper

    @Override
    public List<Task> processDurations(List<Task> tasks) {
        // Ensure tasks is not null or empty
        if (tasks == null || tasks.isEmpty()) {
            throw new IllegalArgumentException("Tasks list cannot be null or empty");
        }

        try {
            // Create HTTP headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Convert the list of tasks to JSON using ObjectMapper (Jackson)
            String requestBody = objectMapper.writeValueAsString(tasks);

            // Create the HttpEntity with headers and the request body (serialized JSON)
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            log.info("Entity :: {}", entity);

            // Make the REST call with just the list of tasks, expecting Task[] as response
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://34.201.173.216:5000/tasks/generate_durations",
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // Check response status and handle errors
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new RuntimeException("Failed to process task durations");
            }

            log.info("Response Body :: {}", response.getBody());

            // Deserialize the JSON response using JsonNode
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            JsonNode tasksNode = rootNode.get("tasks");

            // Convert the response JSON back to a list of Task objects
            return objectMapper.readValue(tasksNode.toString(), new TypeReference<>() {
            });

        } catch (Exception e) {
            log.error("Error processing task durations", e);
            throw new RuntimeException("Error processing task durations", e);
        }
    }
}
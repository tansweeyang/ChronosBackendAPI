package dev.eislyn.chronos_calendar.service;

import dev.eislyn.chronos_calendar.model.Task;

import java.util.List;

public interface ITaskProcessingService {
    List<Task> processDurations(List<Task> tasks);
}

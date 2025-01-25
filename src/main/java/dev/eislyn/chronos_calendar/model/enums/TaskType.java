package dev.eislyn.chronos_calendar.model.enums;

import lombok.Getter;

@Getter
public enum TaskType {
    AUTO(1),
    MANUAL(2);

    private final int code;
    TaskType(int code) {
        this.code = code;
    }

    public static TaskType getStatus(int code) {
        for (TaskType status : TaskType.values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
}

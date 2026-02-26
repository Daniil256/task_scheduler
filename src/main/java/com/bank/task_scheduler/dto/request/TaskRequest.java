package com.bank.task_scheduler.dto.request;

import com.bank.task_scheduler.dto.TaskType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "taskType",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = WorkerTaskRequest.class, name = "TRANSFER"),
        @JsonSubTypes.Type(value = GroupTaskRequest.class, name = "GROUP_STATUS"),
        @JsonSubTypes.Type(value = LogTaskRequest.class, name = "LOG")
})
public abstract class TaskRequest {
    @NotNull(message = "Task type is required")
    TaskType taskType;

    @NotNull(message = "Scheduled time is required")
    @Future(message = "Scheduled time must be in the future")
    LocalDateTime scheduledTime;
}
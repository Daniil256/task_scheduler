package com.bank.task_scheduler.dto;

import com.bank.task_scheduler.model.TaskType;
import com.bank.task_scheduler.model.GroupStatus;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Value
@AllArgsConstructor
public class CreateTaskRequest {
    @NotNull(message = "Task type is required")
    TaskType taskType;

    @NotNull(message = "Scheduled time is required")
    @Future(message = "Scheduled time must be in the future")
    LocalDateTime scheduledTime;

    Long workerId;
    Long targetGroupId;

    Long groupId;

    GroupStatus groupStatus;

    String message;
}
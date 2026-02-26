package com.bank.task_scheduler.dto.response;

import com.bank.task_scheduler.dto.TaskStatus;
import com.bank.task_scheduler.dto.TaskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;

@Getter
@Value
@Builder
@AllArgsConstructor
public class LogTaskResponse implements TaskResponse {
    Long id;
    TaskType taskType;
    TaskStatus taskStatus;
    String message;
    LocalDateTime scheduledTime;
    LocalDateTime createdAt;
}

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
public class WorkerTaskResponse implements TaskResponse {
    Long id;
    Long workerId;
    TaskType taskType;
    TaskStatus taskStatus;
    LocalDateTime scheduledTime;
    LocalDateTime createdAt;
}

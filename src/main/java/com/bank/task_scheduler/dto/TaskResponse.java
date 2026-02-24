package com.bank.task_scheduler.dto;

import com.bank.task_scheduler.model.TaskStatus;
import com.bank.task_scheduler.model.TaskType;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Value
@Builder
@AllArgsConstructor
public class TaskResponse {
    Long id;
    TaskType taskType;
    TaskStatus taskStatus;
    LocalDateTime scheduledTime;
    Map<String, String> payload;
}
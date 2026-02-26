package com.bank.task_scheduler.executor;

import com.bank.task_scheduler.dto.TaskStatus;
import com.bank.task_scheduler.dto.TaskType;
import com.bank.task_scheduler.dto.request.LogTaskRequest;
import com.bank.task_scheduler.dto.request.TaskRequest;
import com.bank.task_scheduler.dto.response.LogTaskResponse;
import com.bank.task_scheduler.dto.response.TaskResponse;
import com.bank.task_scheduler.model.Task;
import com.bank.task_scheduler.service.LogService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LogExecutor implements TaskExecutor {
    private final LogService logService;

    public TaskType getSupportedType() {
        return TaskType.LOG;
    }

    @Override
    public void execute(@NonNull Task task) {
        logService.logNotification(task.getNotificationMessage());
    }

    public Task createTask(@NonNull TaskRequest request) {
        LogTaskRequest logRequest = (LogTaskRequest) request;
        return Task.builder()
                .type(request.getTaskType())
                .status(TaskStatus.SCHEDULED)
                .scheduledTime(request.getScheduledTime())
                .notificationMessage(logRequest.getMessage())
                .build();
    }

    @Override
    public TaskResponse toResponse(@NonNull Task task) {
        return LogTaskResponse.builder()
                .id(task.getId())
                .taskType(task.getType())
                .taskStatus(task.getStatus())
                .message(task.getNotificationMessage())
                .scheduledTime(task.getScheduledTime())
                .createdAt(task.getCreatedAt())
                .build();
    }
}

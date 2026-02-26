package com.bank.task_scheduler.executor;

import com.bank.task_scheduler.dto.TaskStatus;
import com.bank.task_scheduler.dto.TaskType;
import com.bank.task_scheduler.dto.request.TaskRequest;
import com.bank.task_scheduler.dto.request.WorkerTaskRequest;
import com.bank.task_scheduler.dto.response.TaskResponse;
import com.bank.task_scheduler.dto.response.WorkerTaskResponse;
import com.bank.task_scheduler.model.Task;
import com.bank.task_scheduler.service.WorkerService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WorkerTransferExecutor implements TaskExecutor {
    private final WorkerService workerService;

    public TaskType getSupportedType() {
        return TaskType.TRANSFER;
    }

    @Override
    public void execute(@NonNull Task task) {
        workerService.transferWorker(task.getWorkerId(), task.getGroupId());
    }

    @Override
    public Task createTask(@NonNull TaskRequest request) {
        WorkerTaskRequest workerRequest = (WorkerTaskRequest) request;
        return Task.builder()
                .type(request.getTaskType())
                .status(TaskStatus.SCHEDULED)
                .scheduledTime(request.getScheduledTime())
                .workerId(workerRequest.getWorkerId())
                .groupId(workerRequest.getGroupId())
                .build();
    }

    @Override
    public TaskResponse toResponse(@NonNull Task task) {
        return WorkerTaskResponse.builder()
                .id(task.getId())
                .workerId(task.getWorkerId())
                .taskType(task.getType())
                .taskStatus(task.getStatus())
                .scheduledTime(task.getScheduledTime())
                .createdAt(task.getCreatedAt())
                .build();
    }
}

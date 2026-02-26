package com.bank.task_scheduler.executor;

import com.bank.task_scheduler.dto.TaskStatus;
import com.bank.task_scheduler.dto.TaskType;
import com.bank.task_scheduler.dto.request.GroupTaskRequest;
import com.bank.task_scheduler.dto.request.TaskRequest;
import com.bank.task_scheduler.dto.response.GroupTaskResponse;
import com.bank.task_scheduler.dto.response.TaskResponse;
import com.bank.task_scheduler.model.Task;
import com.bank.task_scheduler.service.GroupService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GroupStatusExecutor implements TaskExecutor {
    private final GroupService groupService;

    public TaskType getSupportedType() {
        return TaskType.GROUP_STATUS;
    }

    @Override
    public void execute(@NonNull Task task) {
        groupService.updateGroupStatus(task.getGroupId(), task.getGroupStatus());
    }

    @Override
    public Task createTask(@NonNull TaskRequest request) {
        GroupTaskRequest groupRequest = (GroupTaskRequest) request;
        return Task.builder()
                .type(request.getTaskType())
                .status(TaskStatus.SCHEDULED)
                .scheduledTime(request.getScheduledTime())
                .groupId(groupRequest.getGroupId())
                .groupStatus(groupRequest.getGroupStatus())
                .build();
    }

    @Override
    public TaskResponse toResponse(@NonNull Task task) {
        return GroupTaskResponse.builder()
                .id(task.getId())
                .groupId(task.getGroupId())
                .groupStatus(task.getGroupStatus())
                .taskType(task.getType())
                .taskStatus(task.getStatus())
                .scheduledTime(task.getScheduledTime())
                .createdAt(task.getCreatedAt())
                .build();
    }
}

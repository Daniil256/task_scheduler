package com.bank.task_scheduler.executor;

import com.bank.task_scheduler.dto.TaskType;
import com.bank.task_scheduler.dto.request.TaskRequest;
import com.bank.task_scheduler.dto.response.TaskResponse;
import com.bank.task_scheduler.model.Task;

public interface TaskExecutor {

    TaskType getSupportedType();

    void execute(Task task);

    Task buildTask(TaskRequest request);

    TaskResponse toResponse(Task task);
}

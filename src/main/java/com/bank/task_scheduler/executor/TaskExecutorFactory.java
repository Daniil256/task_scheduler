package com.bank.task_scheduler.executor;

import com.bank.task_scheduler.dto.TaskType;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class TaskExecutorFactory {
    private final Map<TaskType, TaskExecutor> executors = new EnumMap<>(TaskType.class);

    public TaskExecutorFactory(List<TaskExecutor> executorList) {
        executorList.forEach(ex -> executors.put(ex.getSupportedType(), ex));
    }

    public TaskExecutor getExecutor(@NonNull TaskType type) {
        TaskExecutor executor = executors.get(type);
        if (executor == null) {
            throw new IllegalArgumentException("No executor for type: " + type);
        }
        return executor;
    }
}

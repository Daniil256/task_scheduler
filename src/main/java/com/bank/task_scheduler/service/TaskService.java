package com.bank.task_scheduler.service;

import com.bank.task_scheduler.dto.TaskStatus;
import com.bank.task_scheduler.dto.request.TaskRequest;
import com.bank.task_scheduler.dto.response.TaskResponse;
import com.bank.task_scheduler.exception.CancelTaskException;
import com.bank.task_scheduler.exception.EntityNotFoundException;
import com.bank.task_scheduler.executor.TaskExecutorFactory;
import com.bank.task_scheduler.model.*;
import com.bank.task_scheduler.repository.TaskRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskExecutorFactory executorFactory;

    public TaskResponse createTask(@NonNull TaskRequest request) {
        Task task = executorFactory
                .getExecutor(request.getTaskType())
                .buildTask(request);
        Task saved = taskRepository.save(task);
        return mapToResponse(saved);
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse getTaskById(@NonNull Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found", id));
        return mapToResponse(task);
    }

    @Transactional
    public void cancelTask(@NonNull Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found", id));
        if (task.getStatus() == TaskStatus.SCHEDULED) {
            task.setStatus(TaskStatus.CANCELLED);
        } else {
            throw new CancelTaskException("Only scheduled tasks can be cancelled", id);
        }
    }

    private TaskResponse mapToResponse(@NonNull Task task) {
        return executorFactory
                .getExecutor(task.getType())
                .toResponse(task);
    }
}

package com.bank.task_scheduler.service;

import com.bank.task_scheduler.dao.*;
import com.bank.task_scheduler.dto.*;
import com.bank.task_scheduler.model.*;
import com.bank.task_scheduler.exception.EntityNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskDao taskDao;
    private final WorkerDao workerDao;
    private final GroupDao groupDao;
    private final WorkerService workerService;
    private final GroupService groupService;
    private final LogService logService;
    private final ObjectMapper objectMapper;

    @Transactional
    public TaskResponse createTask(@NonNull CreateTaskRequest request) {
        validateRequest(request);

        String payload = buildPayload(request);

        Task savedTask = taskDao.save(Task.builder()
                .type(request.getTaskType())
                .status(TaskStatus.SCHEDULED)
                .scheduledTime(request.getScheduledTime())
                .payload(payload)
                .build());
        return mapToResponse(savedTask);
    }


    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        return taskDao.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskResponse getTaskById(@NonNull Long id) {
        Task task = taskDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
        return mapToResponse(task);
    }

    @Transactional
    public void cancelTask(@NonNull Long id) {
        if (!taskDao.update(id, TaskStatus.CANCELLED)) {
            throw new EntityNotFoundException("Task not found with id: " + id);
        }
    }

    @Transactional
    public void processPendingTasks() {
        LocalDateTime now = LocalDateTime.now();
        List<Task> pendingTasks = taskDao.findByStatusScheduled(TaskStatus.SCHEDULED, now);
        for (Task task : pendingTasks) {
            try {
                executeTask(task);
                task.setStatus(TaskStatus.COMPLETED);
                log.info("Task {} completed successfully", task.getId());
            } catch (Exception e) {
                log.error("Failed to execute task {}: {}", task.getId(), e.getMessage(), e);
                task.setStatus(TaskStatus.FAILED);
            }
        }
        taskDao.batchUpdate(pendingTasks);

    }

    private void validateRequest(CreateTaskRequest request) {
        switch (request.getTaskType()) {
            case TRANSFER:
                if (request.getWorkerId() == null || request.getTargetGroupId() == null) {
                    throw new IllegalArgumentException("workerId and targetGroupId are required for TRANSFER task");
                }
                if (workerDao.findById(request.getWorkerId()).isEmpty()) {
                    throw new EntityNotFoundException("Worker not found with id: " + request.getWorkerId());
                }
                if (groupDao.findById(request.getTargetGroupId()).isEmpty()) {
                    throw new EntityNotFoundException("Target group not found with id: " + request.getTargetGroupId());
                }
                break;
            case GROUP_STATUS:
                if (request.getGroupId() == null || request.getGroupStatus() == null) {
                    throw new IllegalArgumentException("groupId and newStatus are required for GROUP_STATUS task");
                }
                if (groupDao.findById(request.getGroupId()).isEmpty()) {
                    throw new EntityNotFoundException("Group not found with id: " + request.getGroupId());
                }
                break;
            case LOG:
                if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
                    throw new IllegalArgumentException("message is required for LOG task");
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported task type: " + request.getTaskType());
        }
    }

    private String buildPayload(CreateTaskRequest request) {
        Map<String, Object> map = new HashMap<>();
        switch (request.getTaskType()) {
            case TRANSFER:
                map.put("workerId", request.getWorkerId());
                map.put("targetGroupId", request.getTargetGroupId());
                break;
            case GROUP_STATUS:
                map.put("groupId", request.getGroupId());
                map.put("newStatus", request.getGroupStatus().name());
                break;
            case LOG:
                map.put("message", request.getMessage());
                break;
        }
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize payload", e);
        }
    }

    private void executeTask(Task task) throws Exception {
        Map<String, Object> payload = objectMapper.readValue(task.getPayload(), new TypeReference<>() {
        });

        switch (task.getType()) {
            case TRANSFER:
                Long workerId = ((Number) payload.get("workerId")).longValue();
                Long targetGroupId = ((Number) payload.get("targetGroupId")).longValue();
                workerService.transferWorker(workerId, targetGroupId);
                break;
            case GROUP_STATUS:
                Long groupId = ((Number) payload.get("groupId")).longValue();
                GroupStatus newStatus = GroupStatus.valueOf((String) payload.get("newStatus"));
                groupService.changeGroupStatus(groupId, newStatus);
                break;
            case LOG:
                String message = (String) payload.get("message");
                logService.logNotification(message);
                break;
            default:
                throw new IllegalArgumentException("Unknown task type: " + task.getType());
        }
    }

    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .taskType(task.getType())
                .taskStatus(task.getStatus())
                .scheduledTime(task.getScheduledTime())
                .payload(deserializePayload(task.getPayload()))
                .build();
    }

    private Map<String, String> deserializePayload(String payload) {
        try {
            return objectMapper.readValue(payload, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize payload", e);
        }
    }
}
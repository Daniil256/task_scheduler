package com.bank.task_scheduler.scheduling;

import com.bank.task_scheduler.executor.TaskExecutorFactory;
import com.bank.task_scheduler.model.Task;
import com.bank.task_scheduler.dto.TaskStatus;
import com.bank.task_scheduler.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TasksScheduler {
    private final TaskRepository taskRepository;
    private final TaskExecutorFactory executorFactory;

    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void processScheduledTasks() {
        LocalDateTime now = LocalDateTime.now();
        List<Task> pendingTasks = taskRepository.findByStatusAndScheduledTimeLessThanEqual(TaskStatus.SCHEDULED, now);

        for (Task task : pendingTasks) {
            try {
                executorFactory.getExecutor(task.getType()).execute(task);
                task.setStatus(TaskStatus.COMPLETED);
            } catch (Exception e) {
                task.setStatus(TaskStatus.FAILED);
                throw e;
            }
            taskRepository.save(task);
        }
    }
}

package com.bank.task_scheduler.scheduling;

import com.bank.task_scheduler.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TasksScheduler {

    private final TaskService taskService;

    @Scheduled(fixedDelay = 10000)
    public void processScheduledTasks() {
        log.info("Starting scheduled tasks processing");
        try {
            taskService.processPendingTasks();
        } catch (Exception e) {
            log.error("Error during scheduled tasks processing: {}", e.getMessage(), e);
        }
        log.info("Finished scheduled tasks processing");
    }
}
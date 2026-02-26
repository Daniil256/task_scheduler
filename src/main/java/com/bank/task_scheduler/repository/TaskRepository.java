package com.bank.task_scheduler.repository;

import com.bank.task_scheduler.model.Task;
import com.bank.task_scheduler.dto.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatusAndScheduledTimeLessThanEqual(TaskStatus status, LocalDateTime now);
}

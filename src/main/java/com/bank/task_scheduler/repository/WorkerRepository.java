package com.bank.task_scheduler.repository;

import com.bank.task_scheduler.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}

package com.bank.task_scheduler.repository;

import com.bank.task_scheduler.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}

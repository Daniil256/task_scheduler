package com.bank.task_scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EntityScan
@EnableJpaRepositories
public class TaskSchedulerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskSchedulerApplication.class, args);
    }
}

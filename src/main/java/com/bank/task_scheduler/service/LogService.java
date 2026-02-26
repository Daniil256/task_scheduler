package com.bank.task_scheduler.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class LogService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public void logNotification(@NonNull String message) {
        log.info("[NOTIFICATION] {}: {}", LocalDateTime.now().format(FORMATTER), message);
    }
}

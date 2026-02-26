package com.bank.task_scheduler.exception;

import lombok.Getter;

@Getter
public class CancelTaskException extends RuntimeException {
    private final Long taskId;

    public CancelTaskException(String message, Long taskId) {
        super(message);
        this.taskId = taskId;
    }
}

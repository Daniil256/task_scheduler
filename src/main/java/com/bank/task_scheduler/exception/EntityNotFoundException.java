package com.bank.task_scheduler.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private final Long entityId;

    public EntityNotFoundException(String message, Long entityId) {
        super(message);
        this.entityId = entityId;
    }
}

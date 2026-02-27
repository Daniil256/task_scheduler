package com.bank.task_scheduler.dto.response;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;

@Getter
@Value
@Builder
@AllArgsConstructor
public class ErrorResponse {
    Integer status;
    String message;
    LocalDateTime timestamp;
}

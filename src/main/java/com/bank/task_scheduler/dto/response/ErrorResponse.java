package com.bank.task_scheduler.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Data
@Value
@Builder
@AllArgsConstructor
public class ErrorResponse {
    int status;
    String message;
    LocalDateTime timestamp;
}

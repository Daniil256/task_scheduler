package com.bank.task_scheduler.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WorkerTaskRequest extends TaskRequest {

    @NotNull(message = "workerId is required")
    Long workerId;

    @NotNull(message = "groupId is required")
    Long groupId;
}

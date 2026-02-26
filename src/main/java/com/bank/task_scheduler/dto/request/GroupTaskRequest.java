package com.bank.task_scheduler.dto.request;

import com.bank.task_scheduler.dto.GroupStatus;
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
public class GroupTaskRequest extends TaskRequest {

    @NotNull(message = "groupId is required")
    Long groupId;

    @NotNull(message = "groupStatus is required")
    GroupStatus groupStatus;
}

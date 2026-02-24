package com.bank.task_scheduler.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.With;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @With
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private GroupStatus status;
}
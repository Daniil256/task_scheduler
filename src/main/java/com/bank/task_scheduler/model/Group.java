package com.bank.task_scheduler.model;

import com.bank.task_scheduler.dto.GroupStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.With;

import javax.persistence.*;

@Entity
@Table(name = "groups")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @With
    private Long groupId;

    @Column(nullable = false)
    @NonNull
    private String groupName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NonNull
    private GroupStatus groupStatus;
}
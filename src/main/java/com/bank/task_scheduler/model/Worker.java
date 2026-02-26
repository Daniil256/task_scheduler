package com.bank.task_scheduler.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.With;

import javax.persistence.*;

@Entity
@Table(name = "workers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @With
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    @NonNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Group group;
}
package com.bank.task_scheduler.service;

import com.bank.task_scheduler.exception.EntityNotFoundException;
import com.bank.task_scheduler.model.Group;
import com.bank.task_scheduler.model.Worker;
import com.bank.task_scheduler.repository.GroupRepository;
import com.bank.task_scheduler.repository.WorkerRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkerService {
    private final WorkerRepository workerRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public void transferWorker(@NonNull Long workerId, @NonNull Long groupId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new EntityNotFoundException("Worker not found", workerId));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found", workerId));

        worker.setGroup(group);
    }
}

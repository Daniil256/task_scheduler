package com.bank.task_scheduler.service;

import com.bank.task_scheduler.dao.GroupDao;
import com.bank.task_scheduler.dao.WorkerDao;
import com.bank.task_scheduler.exception.EntityNotFoundException;
import com.bank.task_scheduler.model.Group;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerDao workerDao;
    private final GroupDao groupDao;

    @Transactional
    public void transferWorker(@NonNull Long workerId, @NonNull Long targetGroupId) {

        Group targetGroup = groupDao.findById(targetGroupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + targetGroupId));

        if (!workerDao.update(workerId, targetGroup)) {
            throw new EntityNotFoundException("Worker not found with id: " + workerId);
        }
    }
}
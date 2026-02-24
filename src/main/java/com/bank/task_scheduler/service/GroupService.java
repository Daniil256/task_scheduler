package com.bank.task_scheduler.service;

import com.bank.task_scheduler.exception.EntityNotFoundException;
import com.bank.task_scheduler.model.GroupStatus;
import com.bank.task_scheduler.dao.GroupDao;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupDao groupDao;

    @Transactional
    public void changeGroupStatus(@NonNull Long groupId, @NonNull GroupStatus newStatus) {
        if (!groupDao.update(groupId, newStatus)) {
            throw new EntityNotFoundException("Group not found with id: " + groupId);
        }
    }
}
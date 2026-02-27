package com.bank.task_scheduler.service;

import com.bank.task_scheduler.exception.EntityNotFoundException;
import com.bank.task_scheduler.dto.GroupStatus;
import com.bank.task_scheduler.model.Group;
import com.bank.task_scheduler.repository.GroupRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    @Transactional
    public void updateGroupStatus(@NonNull Long groupId, @NonNull GroupStatus newStatus) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found", groupId));

        group.setGroupStatus(newStatus);
    }
}

package com.bank.task_scheduler.dao.mapper;

import com.bank.task_scheduler.model.Group;
import com.bank.task_scheduler.model.GroupStatus;
import com.bank.task_scheduler.model.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class WorkerRowMapper implements RowMapper<Worker> {

    @Override
    public Worker mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Worker.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .group(Group.builder()
                        .id(rs.getLong("group_id"))
                        .name(rs.getString("group_name"))
                        .status(GroupStatus.valueOf(rs.getString("group_status")))
                        .build())
                .build();
    }
}

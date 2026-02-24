package com.bank.task_scheduler.dao.mapper;

import com.bank.task_scheduler.model.Group;
import com.bank.task_scheduler.model.GroupStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GroupRowMapper implements RowMapper<Group> {
    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Group.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .status(GroupStatus.valueOf(rs.getString("status")))
                .build();
    }
}

package com.bank.task_scheduler.dao.mapper;

import com.bank.task_scheduler.model.Task;
import com.bank.task_scheduler.model.TaskType;
import com.bank.task_scheduler.model.TaskStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class TaskRowMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Task.builder()
                .id(rs.getLong("id"))
                .type(TaskType.valueOf(rs.getString("type")))
                .status(TaskStatus.valueOf(rs.getString("status")))
                .payload(rs.getString("payload"))
                .scheduledTime(getTimestamp(rs, "scheduled_time"))
                .createdAt(getTimestamp(rs, "created_at"))
                .updatedAt(getTimestamp(rs, "updated_at"))
                .build();
    }

    private LocalDateTime getTimestamp(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getObject(columnName, Timestamp.class);
        return rs.wasNull() ? null : timestamp.toLocalDateTime();
    }
}

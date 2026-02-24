package com.bank.task_scheduler.dao;

import com.bank.task_scheduler.model.Task;
import com.bank.task_scheduler.model.TaskStatus;
import com.bank.task_scheduler.dao.mapper.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final TaskRowMapper rowMapper;

    private static final String INSERT = "INSERT INTO tasks (type, status, scheduled_time, payload, created_at, updated_at) " +
            "VALUES (:type, :status, :scheduledTime, :payload::jsonb, :createdAt, :updatedAt)";

    private static final String UPDATE_STATUS = "UPDATE tasks SET status = :status, updated_at = :updatedAt WHERE id = :id";

    private static final String FIND_BY_ID = "SELECT * FROM tasks WHERE id = :id";

    private static final String FIND_ALL = "SELECT * FROM tasks";

    private static final String DELETE_BY_ID = "DELETE FROM tasks WHERE id = :id";

    private static final String FIND_BY_STATUS_SCHEDULED = "SELECT * FROM tasks " +
            "WHERE status = :status AND scheduled_time <= :scheduledTime";

    public Task save(Task task) {
        LocalDateTime now = LocalDateTime.now();
        task.setCreatedAt(now);
        task.setUpdatedAt(now);
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("type", task.getType().name())
                .addValue("status", task.getStatus().name())
                .addValue("scheduledTime", Timestamp.valueOf(task.getScheduledTime()))
                .addValue("payload", task.getPayload())
                .addValue("createdAt", Timestamp.valueOf(task.getCreatedAt()))
                .addValue("updatedAt", Timestamp.valueOf(task.getUpdatedAt()));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT, params, keyHolder, new String[]{"id"});

        Number key = keyHolder.getKey();
        if (key != null) {
            task.setId(key.longValue());
        }
        return task;
    }

    public boolean update(Long id, TaskStatus status) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("status", status.name())
                .addValue("updatedAt", LocalDateTime.now());

        return jdbcTemplate.update(UPDATE_STATUS, params) > 0;
    }

    public void batchUpdate(List<Task> tasks) {
        LocalDateTime now = LocalDateTime.now();
        MapSqlParameterSource[] sources = tasks.stream().map(t -> new MapSqlParameterSource()
                .addValue("id", t.getId())
                .addValue("status", t.getStatus().name())
                .addValue("updatedAt", now)).toArray(MapSqlParameterSource[]::new);

        jdbcTemplate.batchUpdate(UPDATE_STATUS, sources);
    }

    public Optional<Task> findById(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        try {
            Task task = jdbcTemplate.queryForObject(FIND_BY_ID, params, rowMapper);
            return Optional.ofNullable(task);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Task> findAll() {
        return jdbcTemplate.query(FIND_ALL, rowMapper);
    }

    public void deleteById(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        jdbcTemplate.update(DELETE_BY_ID, params);
    }

    public List<Task> findByStatusScheduled(TaskStatus status, LocalDateTime now) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("status", status.name())
                .addValue("scheduledTime", Timestamp.valueOf(now));

        return jdbcTemplate.query(FIND_BY_STATUS_SCHEDULED, params, rowMapper);
    }
}

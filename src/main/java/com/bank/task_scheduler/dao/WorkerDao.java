package com.bank.task_scheduler.dao;

import com.bank.task_scheduler.model.Group;
import com.bank.task_scheduler.model.Worker;
import com.bank.task_scheduler.dao.mapper.WorkerRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WorkerDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final WorkerRowMapper rowMapper;

    private static final String INSERT = "INSERT INTO workers (name, group_id) VALUES (:name, :groupId)";
    private static final String FIND_BY_ID = "SELECT w.id, w.name, g.id as group_id," +
            " g.name as group_name, g.status as group_status " +
            "FROM workers w JOIN groups g ON w.group_id = g.id WHERE w.id = :id";

    private static final String UPDATE = "UPDATE workers SET group_id = :groupId WHERE id = :id";


    public void save(Worker worker) {
        jdbcTemplate.update(INSERT, new MapSqlParameterSource()
                .addValue("name", worker.getName())
                .addValue("groupId", worker.getGroup().getId()));
    }

    public boolean update(Long id, Group targetGroup) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("groupId", targetGroup.getId());
        return jdbcTemplate.update(UPDATE, params) > 0;
    }

    public Optional<Worker> findById(Long id) {
        try {
            Worker worker = jdbcTemplate.queryForObject(FIND_BY_ID,
                    new MapSqlParameterSource().addValue("id", id),
                    rowMapper);
            return Optional.ofNullable(worker);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
package com.bank.task_scheduler.dao;

import com.bank.task_scheduler.dao.mapper.GroupRowMapper;
import com.bank.task_scheduler.model.Group;
import com.bank.task_scheduler.model.GroupStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GroupDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final GroupRowMapper rowMapper;

    private static final String INSERT = "INSERT INTO groups (name, status) VALUES (:name, :status)";
    private static final String FIND_BY_ID = "SELECT * FROM groups WHERE id = :id";
    private static final String UPDATE = "UPDATE groups SET status = :status WHERE id = :id";

    public void save(Group group) {
        jdbcTemplate.update(INSERT, new MapSqlParameterSource()
                .addValue("name", group.getName())
                .addValue("status", group.getStatus().name()));
    }

    public boolean update(Long groupId, GroupStatus newStatus) {
        return jdbcTemplate.update(UPDATE, new MapSqlParameterSource()
                .addValue("id", groupId)
                .addValue("status", newStatus.name())) > 0;
    }

    public Optional<Group> findById(Long id) {
        try {
            Group group = jdbcTemplate.queryForObject(FIND_BY_ID,
                    new MapSqlParameterSource().addValue("id", id),
                    rowMapper);
            return Optional.ofNullable(group);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
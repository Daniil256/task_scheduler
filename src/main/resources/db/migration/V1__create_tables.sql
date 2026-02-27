CREATE SEQUENCE IF NOT EXISTS groups_seq START WITH 1;

CREATE TABLE IF NOT EXISTS groups
(
    group_id     BIGINT PRIMARY KEY DEFAULT nextval('groups_seq'),
    group_name   VARCHAR(255) NOT NULL,
    group_status VARCHAR(20)  NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS workers_seq START WITH 1;

CREATE TABLE IF NOT EXISTS workers
(
    worker_id   BIGINT PRIMARY KEY DEFAULT nextval('workers_seq'),
    worker_name VARCHAR(255) NOT NULL,
    status      VARCHAR(20)  NOT NULL,
    group_id    BIGINT       NOT NULL,
    CONSTRAINT fk_workers_group FOREIGN KEY (group_id)
        REFERENCES groups (group_id) ON DELETE SET NULL
);
CREATE SEQUENCE IF NOT EXISTS tasks_seq START WITH 1;

CREATE TABLE IF NOT EXISTS tasks
(
    task_id              BIGINT PRIMARY KEY DEFAULT nextval('tasks_seq'),
    type                 VARCHAR(50) NOT NULL,
    status               VARCHAR(50) NOT NULL,
    scheduled_time       TIMESTAMP   NOT NULL,
    created_at           TIMESTAMP,
    updated_at           TIMESTAMP,
    worker_id            BIGINT,
    group_id             BIGINT,
    group_status         VARCHAR(20),
    notification_message TEXT
);

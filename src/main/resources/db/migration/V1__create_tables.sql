CREATE TABLE IF NOT EXISTS groups
(
    id     BIGSERIAL PRIMARY KEY,
    name   VARCHAR(255) NOT NULL,
    status VARCHAR(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS workers
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    group_id BIGINT REFERENCES groups (id)
);

CREATE TABLE IF NOT EXISTS tasks
(
    id             BIGSERIAL PRIMARY KEY,
    type           VARCHAR(20) NOT NULL,
    status         VARCHAR(20) NOT NULL,
    scheduled_time TIMESTAMP   NOT NULL,
    created_at     TIMESTAMP DEFAULT NOW(),
    updated_at     TIMESTAMP DEFAULT NOW()
);

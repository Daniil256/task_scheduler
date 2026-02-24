-- Создание таблицы groups
CREATE TABLE IF NOT EXISTS groups
(
    id     BIGSERIAL PRIMARY KEY,
    name   VARCHAR(255) NOT NULL,
    status VARCHAR(20)  NOT NULL
);

-- Создание таблицы workers
CREATE TABLE IF NOT EXISTS workers
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    group_id BIGINT REFERENCES groups (id)
);

-- Создание таблицы tasks
CREATE TABLE IF NOT EXISTS tasks
(
    id             BIGSERIAL PRIMARY KEY,
    type           VARCHAR(20) NOT NULL,
    status         VARCHAR(20) NOT NULL,
    scheduled_time TIMESTAMP   NOT NULL,
    payload        JSONB,
    created_at     TIMESTAMP DEFAULT NOW(),
    updated_at     TIMESTAMP DEFAULT NOW()
);
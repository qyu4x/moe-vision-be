CREATE TABLE streams
(
    id               VARCHAR(255) NOT NULL PRIMARY KEY,
    title            VARCHAR(255) NOT NULL,
    full_name        VARCHAR(100) NOT NULL,
    token            VARCHAR(255) NOT NULL UNIQUE,
    is_token_active  BOOLEAN      NOT NULL DEFAULT TRUE,
    is_stream_active BOOLEAN      NOT NULL DEFAULT FALSE,
    expired_at       BIGINT       NOT NULL DEFAULT 0,
    created_at       BIGINT       NOT NULL DEFAULT 0,
    updated_at       BIGINT       NOT NULL DEFAULT 0

);

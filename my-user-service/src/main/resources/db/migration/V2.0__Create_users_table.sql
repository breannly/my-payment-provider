CREATE TABLE person.users
(
    id           BIGSERIAL PRIMARY KEY,
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    profile_uid  UUID         NOT NULL REFERENCES person.profiles (uid),
    email        VARCHAR(255) UNIQUE,
    phone_number VARCHAR(50)  UNIQUE,
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    status       VARCHAR(50)  NOT NULL DEFAULT 'IS_PENDING',
    filled       BOOLEAN,
    verified_at  TIMESTAMP,
    archived_at  TIMESTAMP,
    applicant_id VARCHAR(255)
);
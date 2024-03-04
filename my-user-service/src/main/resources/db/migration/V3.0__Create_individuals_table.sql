CREATE TABLE person.individuals
(
    id                       BIGSERIAL PRIMARY KEY,
    created_at               TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at               TIMESTAMP          DEFAULT CURRENT_TIMESTAMP,
    user_id                  BIGSERIAL NOT NULL,
    date_of_birth            TIMESTAMP NOT NULL,
    passport_number          VARCHAR(32),
    personal_identity_number VARCHAR(64),
    gender                   VARCHAR(10),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES person.users (id) ON DELETE CASCADE
);
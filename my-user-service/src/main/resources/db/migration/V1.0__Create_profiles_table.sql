CREATE TABLE person.profiles
(
    uid               UUID                  DEFAULT uuid_generate_v4() PRIMARY KEY,
    created_at        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    username          VARCHAR(128) NOT NULL,
    password          TEXT         NOT NULL,
    enabled           BOOLEAN               DEFAULT true,
    is_admin          BOOLEAN               DEFAULT false,
    last_profile_uid  UUID,
    language          CHAR(2)               DEFAULT 'EN',
    email_verified    BOOLEAN               DEFAULT false,
    prefer_mobile_2fa BOOLEAN               DEFAULT false,
    profile_type      VARCHAR(24),
    is_password_set   BOOLEAN               DEFAULT false,
    is_social         BOOLEAN               DEFAULT false,
    is_2fa_enabled    BOOLEAN               DEFAULT false,
    secret_key        VARCHAR(64)
);
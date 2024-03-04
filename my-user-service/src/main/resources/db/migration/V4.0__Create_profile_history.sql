create table person.profile_history
(
    id                  BIGSERIAL PRIMARY KEY,
    created_at          TIMESTAMP NOT NULL default CURRENT_TIMESTAMP,
    target_profile_uid  uuid      NOT NULL references person.profiles,
    reason              varchar,
    comment             varchar(512),
    changed_by_user_uid BIGSERIAL NOT NULL references person.users,
    changed_values      jsonb     NOT NULL
);
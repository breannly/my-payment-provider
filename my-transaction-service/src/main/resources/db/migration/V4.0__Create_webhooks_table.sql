create table if not exists transaction.webhooks
(
    uid              uuid      default uuid_generate_v4() not null primary key,
    transaction_uid  uuid                                 not null references transaction.transactions on delete cascade,
    created_at       timestamp default now()              not null,
    updated_at       timestamp default now(),
    transaction_data jsonb                                not null,
    count            smallserial                          not null,
    status_code      varchar(64),
    message          text
);
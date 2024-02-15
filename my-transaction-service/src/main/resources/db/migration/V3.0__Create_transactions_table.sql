create table if not exists transaction.transactions
(
    uid              uuid      default uuid_generate_v4() not null primary key,
    type             varchar(16)                          not null,
    merchant_uid     uuid                                 not null,
    payment_method   varchar(128)                         not null,
    amount           decimal                              not null,
    currency         varchar(3)                           not null,
    created_at       timestamp default now()              not null,
    updated_at       timestamp default now(),
    card_data        jsonb                                not null,
    language         varchar(2)                           not null,
    notification_url text                                 not null,
    customer         jsonb                                not null,
    status           varchar(16),
    message          varchar(64)
);
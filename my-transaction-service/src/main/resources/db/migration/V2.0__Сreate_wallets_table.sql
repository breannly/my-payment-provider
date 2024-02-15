create table if not exists transaction.wallets
(
    uid          uuid      default uuid_generate_v4() not null primary key,
    merchant_uid uuid                                 not null references transaction.merchants on delete cascade,
    currency     varchar(3)                           not null,
    balance      decimal,
    created_at   timestamp default now()              not null,
    updated_at   timestamp default now()
);
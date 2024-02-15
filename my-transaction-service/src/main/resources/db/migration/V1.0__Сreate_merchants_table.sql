create table if not exists transaction.merchants
(
    uid        uuid default uuid_generate_v4() not null primary key,
    secret_key text
);
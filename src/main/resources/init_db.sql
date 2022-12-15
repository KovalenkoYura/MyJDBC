create table public.manufacturers
(
    id         integer primary key generated always as identity,
    name       varchar(50) not null,
    country    varchar(50) not null,
    is_deleted boolean     not null
);
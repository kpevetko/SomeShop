DROP table products;
drop table users;
drop table purchases;
drop table authorities;

create table authorities(
  id serial not null,
  username varchar,
  authority varchar
);

alter table authorities
    owner to postgres;

create table products
(
    id          serial not null,
    name        varchar,
    description varchar,
    cost        integer,
    number      integer
);

alter table products
    owner to postgres;

create table users
(
    id       serial not null,
    username    varchar,
    password varchar,
    enabled     boolean
);

alter table users
    owner to postgres;

create table purchases
(
    id          serial not null,
    "id_user"    integer,
    "id_product" integer,
    date        date,
    "cost_all"   integer,
    "number_all" integer
);

alter table purchases
    owner to postgres;

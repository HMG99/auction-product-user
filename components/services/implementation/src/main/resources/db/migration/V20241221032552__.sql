CREATE TABLE auction.user
(
    user_id           UUID primary key not null,
    first_name        varchar(64)      not null,
    last_name         varchar(64)      not null,
    year              int              not null,
    email             varchar(64)      not null unique,
    password          varchar(64)      not null,
    role              varchar(12)      not null,
    status            varchar(12)      not null,
    verification_code varchar(15),
    reset_token       varchar(15)
);


CREATE TABLE auction.product
(
    product_id   UUID primary key not null,
    product_code varchar(15) unique,
    product_name varchar(64)      not null,
    category     varchar(15)
);


CREATE TABLE auction.product_user
(
    product_user_id UUID primary key not null,
    product_id      UUID             not null,
    user_id         UUID             not null,
    description     varchar(512)     not null,
    count           int,
    price           varchar(64)      not null
);
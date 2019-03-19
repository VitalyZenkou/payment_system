CREATE DATABASE payment_system;

CREATE TABLE address (
  customer_id  int          NOT NULL,
  city         varchar(50)  NOT NULL,
  street       varchar(128) NOT NULL,
  house_number int          NOT NULL,
  flat_number  int,
  FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE TABLE "user" (
  id               serial PRIMARY KEY,
  login            varchar(50)  NOT NULL UNIQUE,
  password         varchar(300) NOT NULL,
  name             varchar(50)  NOT NULL,
  surname          varchar(50)  NOT NULL,
  is_administrator boolean      NOT NULL DEFAULT false
);

CREATE TABLE credit_card (
  number           varchar(50)    NOT NULL UNIQUE,
  validity_date    varchar(50)    NOT NULL,
  pin_code         smallint       NOT NULL,
  cvv              smallint       NOT NULL,
  balance          numeric(10, 2) NOT NUlL DEFAULT 0.00,
  is_blocked       boolean        NOT NULL DEFAULT false,
  customer_id      bigint,
  credit_card_type smallint       NOT NULL DEFAULT 1,
  FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE TABLE customer (
  id           serial PRIMARY KEY,
  phone_number varchar(50),
  user_id      smallint NOT NULL,
  FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE payment (
  id           serial PRIMARY KEY,
  number       varchar(50)    NOT NULL,
  cost         numeric(10, 2) NOT NULL DEFAULT 0.00,
  organization varchar(128)   NOT NULL DEFAULT '',
  customer_id  smallint,
  FOREIGN KEY (customer_id) REFERENCES customer (id)
);
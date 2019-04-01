CREATE TABLE "user"
(
  id               serial PRIMARY KEY,
  login            varchar(50)  NOT NULL UNIQUE,
  password         varchar(300) NOT NULL,
  name             varchar(50)  NOT NULL,
  surname          varchar(50)  NOT NULL,
  is_administrator boolean      NOT NULL DEFAULT false
);

CREATE TABLE address
(
  id           serial PRIMARY KEY,
  user_id      int          NOT NULL,
  city         varchar(50)  NOT NULL,
  street       varchar(128) NOT NULL,
  house_number int          NOT NULL,
  phone_number varchar(50),
  flat_number  int,
  FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE credit_card
(
  id               serial PRIMARY KEY,
  user_id          bigint,
  number           varchar(50)    NOT NULL UNIQUE,
  validity_date    varchar(50)    NOT NULL,
  pin_code         smallint       NOT NULL,
  cvv              smallint       NOT NULL,
  balance          numeric(10, 2) NOT NUlL DEFAULT 0.00,
  is_blocked       boolean        NOT NULL DEFAULT false,
  credit_card_type varchar(20)    NOT NULL DEFAULT 'visa',
  FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE payment
(
  id                    serial PRIMARY KEY,
  credit_card_number    varchar(50)    NOT NULL,
  cost                  numeric(10, 2) NOT NULL DEFAULT 0.00,
  organization          varchar(128)   NOT NULL DEFAULT '',
  to_credit_card_number varchar(50)    NOT NULL DEFAULT '',
  is_transaction        boolean        NOT NULL DEFAULT false,
  user_id               bigint,
  FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE credit_card_type
(
  id   serial PRIMARY KEY,
  name varchar(20) NOT NULL UNIQUE
);

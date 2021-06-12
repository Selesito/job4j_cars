CREATE TABLE car (
    id serial primary key,
    name text
);

CREATE TABLE author (
    id serial primary key,
    name text,
    email VARCHAR (50) UNIQUE,
    password TEXT
);

CREATE TABLE body_type (
     id serial primary key,
     name text
);

CREATE TABLE ads (
    id serial primary key,
    description text,
    created     timestamp,
    status       boolean,
    photo varchar(255),
    car_id int not null references car (id),
    author_id int not null references author (id),
    body_type_id int not null references body_type (id)
);




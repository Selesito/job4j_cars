CREATE TABLE engine (
    id serial primary key,
    name text
);

CREATE TABLE transmission (
    id serial primary key,
    name text
);

CREATE TABLE brand (
     id serial primary key,
     name text
);

CREATE TABLE color (
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

CREATE TABLE model (
    id serial primary key,
    name text,
    brand_id int not null references brand (id)
);

CREATE TABLE car (
    id serial primary key,
    mileage text,
    year text,
    owners text,
    power varchar(255),
    vinNumber text UNIQUE,
    body_type_id int not null references body_type (id),
    engine_id int not null references engine (id),
    transmission_id int not null references transmission (id),
    color_id int not null references color (id),
    model_id int not null references model (id)
);

CREATE TABLE ads (
    id serial primary key,
    title text,
    description text,
    created     timestamp,
    status       boolean,
    photo varchar(255),
    price varchar(255),
    car_id int not null references car (id),
    author_id int not null references author (id)
);

create table photo (
    id serial primary key,
    path varchar(255) not null unique,
    ad_id int references ads(id)
);

insert into engine (name) values
    ('Бензиновый'),
    ('Дизельный'),
    ('Гибридный'),
    ('Электрический');

insert into transmission (name) values
    ('MT'),
    ('AT'),
    ('CVT'),
    ('DSG');

insert into color (name) values
    ('Белый'),
    ('Красный'),
    ('Синий'),
    ('Коричневый'),
    ('Чёрный'),
    ('Серебристый'),
    ('Желтый'),
    ('Баклажан'),
    ('Зеленый');

insert into brand (name) values
    ('BMW'),
    ('Mercedes'),
    ('Nissan'),
    ('Toyota'),
    ('Volkswagen');

insert into model (name, brand_id) values
    ('X1', 1),
    ('X2', 1),
    ('X3', 1),
    ('X4', 1),
    ('X5', 1),
    ('S250', 2),
    ('S300d', 2),
    ('S450d', 2),
    ('S500', 2),
    ('s600', 2),
    ('Teana', 3),
    ('Tiida', 3),
    ('Leaf', 3),
    ('Murano', 3),
    ('Almera', 3),
    ('Corolla', 4),
    ('Camry', 4),
    ('Land Cruiser Prado ', 4),
    ('Land Cruiser 200', 4),
    ('Fielder', 4),
    ('Polo', 5),
    ('Golf', 5),
    ('Passat', 5),
    ('Tiguan', 5),
    ('Touareg', 5);

insert into body_type (name) values
    ('Седан'),
    ('Универсал'),
    ('Хэтчбек'),
    ('Внедорожник'),
    ('Купе');




CREATE TABLE dish
(
    dish_id          bigint PRIMARY KEY NOT NULL,
    balance          bigint             NOT NULL,
    short_name       character varying  NOT NULL,
    dish_composition character varying  NOT NULL
)
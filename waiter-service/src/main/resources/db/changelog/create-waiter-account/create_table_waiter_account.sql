create table waiter_account
(
    waiter_id       bigint PRIMARY KEY       NOT NULL,
    name            character varying        NOT NULL,
    employment_date timestamp with time zone NOT NULL,
    sex             character varying        NOT NULL
)
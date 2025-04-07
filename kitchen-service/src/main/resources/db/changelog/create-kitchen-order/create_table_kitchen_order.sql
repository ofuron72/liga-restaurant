CREATE TABLE kitchen_order
(
    kitchen_order_id bigint PRIMARY KEY       NOT NULL,
    waiter_order_no  bigint                   NOT NULL,
    status           character varying        NOT NULL,
    create_dttm      timestamp with time zone NOT NULL
)
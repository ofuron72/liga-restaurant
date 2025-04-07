create table waiter_order
(
    order_no bigint primary key NOT NULL,
    status      character varying        NOT NULL,
    create_dttm timestamp with time zone NOT NULL,
    waiter_id   bigint                   NOT NULL,
    table_no    character varying        NOT NULL,
    FOREIGN KEY (waiter_id) references waiter_account (waiter_id)
)
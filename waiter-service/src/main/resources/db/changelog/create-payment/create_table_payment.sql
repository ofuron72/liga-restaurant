create table payment
(
    order_no bigint primary key NOT NULL,
    payment_type character varying        NOT NULL,
    payment_date timestamp with time zone NOT NULL,
    payment_sum  numeric,
    foreign key (order_no) references waiter_order (order_no)
)
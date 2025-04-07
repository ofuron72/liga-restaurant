create table order_positions
(
    composition_id   bigint PRIMARY KEY NOT NULL,
    dish_num         bigint             NOT NULL,
    order_no         bigint             NOT NULL,
    menu_position_id bigint NOT NULL,
    foreign key (menu_position_id) references menu (id),
    foreign key (order_no) references waiter_order (order_no)

)
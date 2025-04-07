create table order_to_dish
(
    kitchen_order_id bigint NOT NULL,
    dish_id          bigint NOT NULL,
    dishes_number    bigint NOT NULL,
    PRIMARY KEY (kitchen_order_id, dish_id),
    FOREIGN KEY (kitchen_order_id) REFERENCES kitchen_order (kitchen_order_id),
    FOREIGN KEY (dish_id) REFERENCES dish (dish_id)
)
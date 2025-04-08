INSERT INTO dish (dish_id, balance, short_name, dish_composition)
VALUES (nextval('dish_sequence'), 100, 'Pizza', 'Dough, tomato sauce, cheese, ham'),
       (nextval('dish_sequence'), 80, 'Salad', 'Lettuce leaves, tomatoes, cucumbers'),
       (nextval('dish_sequence'), 120, 'Burger', 'Bun, patty, cheese, lettuce');

INSERT INTO kitchen_order (kitchen_order_id, waiter_order_no, status, create_dttm)
VALUES (nextval('kitchen_order_sequence'), 1, 'CREATED', '2025-04-01 12:00:00+03'),
       (nextval('kitchen_order_sequence'), 1, 'CREATED', '2025-04-01 12:15:00+03'),
       (nextval('kitchen_order_sequence'), 1, 'CREATED', '2025-04-01 12:30:00+03');

INSERT INTO order_to_dish (kitchen_order_id, dish_id, dishes_number)
VALUES (1, 1, 2), -- Заказ 1: 2 пиццы
       (1, 2, 1), -- Заказ 1: 1 салат
       (2, 3, 1), -- Заказ 2: 1 бургер
       (3, 1, 1), -- Заказ 3: 1 пицца
       (3, 2, 2); -- Заказ 3: 2 салата
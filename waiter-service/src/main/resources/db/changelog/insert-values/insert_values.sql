INSERT INTO menu(id, dish_name, dish_cost)
VALUES (nextval('menu_sequence'), 'Pizza', 10.99),
       (nextval('menu_sequence'), 'Salad', 8.49),
       (nextval('menu_sequence'), 'Burger', 12.99);

INSERT INTO waiter_account (waiter_id, name, employment_date, sex)
VALUES (nextval('waiter_account_sequence'), 'John Doe', '2022-01-01 00:00:00+03', 'Male'),
       (nextval('waiter_account_sequence'), 'Jane Smith', '2021-06-01 00:00:00+03', 'Female'),
       (nextval('waiter_account_sequence'), 'Michael Brown', '2020-03-01 00:00:00+03', 'Male');

INSERT INTO waiter_order (order_no, status, create_dttm, waiter_id, table_no)
VALUES (nextval('waiter_order_sequence'), 'ACCEPTED', '2025-04-01 12:00:00+03', 1, 'A1'),
       (nextval('waiter_order_sequence'), 'ACCEPTED', '2025-04-01 12:15:00+03', 2, 'B2'),
       (nextval('waiter_order_sequence'), 'ACCEPTED', '2025-04-01 12:30:00+03', 3, 'C3');

INSERT INTO payment (order_no, payment_type, payment_date, payment_sum)
VALUES (1, 'Cash', '2025-04-01 12:15:00+03', 100.00),
       (2, 'Card', '2025-04-01 12:30:00+03', 150.00),
       (3, 'Cash', '2025-04-01 12:45:00+03', 200.00);

INSERT INTO order_positions (composition_id, dish_num, order_no, menu_position_id)
VALUES (nextval('order_position_sequence'), 2, 1, 1), -- Заказ 1: 2 пиццы
       (nextval('order_position_sequence'), 1, 1, 2), -- Заказ 1: 1 салат
       (nextval('order_position_sequence'), 1, 2, 3), -- Заказ 2: 1 бургер
       (nextval('order_position_sequence'), 1, 3, 1); -- Заказ 3: 1 пицца
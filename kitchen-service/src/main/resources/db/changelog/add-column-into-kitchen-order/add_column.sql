ALTER TABLE if exists kitchen_order
    ADD COLUMN if not exists order_id_in_waiter_service bigint;
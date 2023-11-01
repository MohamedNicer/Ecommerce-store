-- This file allows us to load static data into the test database before tests are run.
-- Passwords will be in the format: Password<UserNumber>1, unless specified otherwise
-- Encrypted Passwords using https://www.javainuse.com/onlineBcrypt
INSERT INTO local_user (email,first_name,last_name,username,password,email_verified)
    VALUES ('user1@mail.com','User1_FirstName','User1_LastName','User1','$2a$10$i.Vdn60rYe1QX7OSqym8n.R4zM/nspzfKL6DE7NWRhA1viA8Ble36',true),
           ('user2@mail.com','User2_FirstName','User2_LastName','User2','$2a$10$PBkmTs9wUDko4kT2wE6KZ.stk6ecP7rQXmZiujpIPJ8iqmX8n5qt2',false),
           ('user3@mail.com', 'User3_FirstName', 'User3_LastName', 'User3','$2a$10$FJmgSQmSDgTKt7zVZJs42uRLpTNlEzOo5B1POWzCidB53Mey5sedi', false);

INSERT INTO address(address_line_1, city, country, user_id)
VALUES ('123 Tester Hill', 'Marrakesh', 'Morocco', 1)
     , ('312 Spring Boot', 'Casablanca', 'Morocco', 3);

INSERT INTO product (name, short_description, long_description, price)
VALUES ('Product #1', 'Product one short description.', 'This is a very long description of product #1.', 5.50)
     , ('Product #2', 'Product two short description.', 'This is a very long description of product #2.', 10.56)
     , ('Product #3', 'Product three short description.', 'This is a very long description of product #3.', 2.74)
     , ('Product #4', 'Product four short description.', 'This is a very long description of product #4.', 15.69)
     , ('Product #5', 'Product five short description.', 'This is a very long description of product #5.', 42.59);

INSERT INTO inventory (product_id, quantity)
VALUES (1, 5)
     , (2, 8)
     , (3, 12)
     , (4, 73)
     , (5, 2);

INSERT INTO web_order (address_id, user_id)
VALUES (1, 1)
     , (1, 1)
     , (1, 1)
     , (2, 3)
     , (2, 3);

INSERT INTO web_order_quantity (order_id, product_id, quantity)
VALUES (1, 1, 5)
     , (1, 2, 5)
     , (2, 3, 5)
     , (2, 2, 5)
     , (2, 5, 5)
     , (3, 3, 5)
     , (4, 4, 5)
     , (4, 2, 5)
     , (5, 3, 5)
     , (5, 1, 5);
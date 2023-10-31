-- Passwords will be in the format: Password<UserNumber>1, unless specified otherwise
-- Encrypted Passwords using https://www.javainuse.com/onlineBcrypt
INSERT INTO local_user (email,first_name,last_name,username,password,email_verified)
    VALUES ('user1@mail.com','User1_FirstName','User1_LastName','User1','$2a$10$i.Vdn60rYe1QX7OSqym8n.R4zM/nspzfKL6DE7NWRhA1viA8Ble36',true),
           ('user2@mail.com','User2_FirstName','User2_LastName','User2','$2a$10$PBkmTs9wUDko4kT2wE6KZ.stk6ecP7rQXmZiujpIPJ8iqmX8n5qt2',false);
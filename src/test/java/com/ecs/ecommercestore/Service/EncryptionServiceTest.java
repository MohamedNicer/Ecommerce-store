package com.ecs.ecommercestore.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class EncryptionServiceTest {
    @Autowired
    private EncryptionService encryptionService;
    @Test
    public void testPasswordEncryption(){
        String password = "password_shouldn't_be_revealed";
        String hash = encryptionService.encryptPassword(password);
        Assertions.assertTrue(encryptionService.verifyPassword(password,hash),"The Original and The Hash Should Match");
        Assertions.assertFalse(encryptionService.verifyPassword(password + "Sike!",hash),"The Two Shouldn't Match");
    }
}

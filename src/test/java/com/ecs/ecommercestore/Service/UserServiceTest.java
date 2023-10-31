package com.ecs.ecommercestore.Service;

import com.ecs.ecommercestore.Api.Model.RegistrationBody;
import com.ecs.ecommercestore.Exception.UserAlreadyExistsException;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @RegisterExtension
    private static GreenMailExtension greenMailExtension = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("Mohamed","secret1"))
            .withPerMethodLifecycle(true);

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void testRegisterUser() throws MessagingException {
        RegistrationBody registrationBody = new RegistrationBody();
        registrationBody.setUsername("User1");
        registrationBody.setEmail("UserServiceTest$testRegisterUser@mail.com");
        registrationBody.setFirstName("First_Name");
        registrationBody.setLastName("Last_Name");
        registrationBody.setPassword("myPassword1");
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(registrationBody),
        "Username Should Already Be Existing and In Use");
        registrationBody.setUsername("UserServiceTest$testRegisterUserUsername");
        registrationBody.setEmail("user1@mail.com");
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(registrationBody),
                "Email Should Already Be Existing and In Use");
        registrationBody.setEmail("UserServiceTest$testRegisterUser@mail.com");
        Assertions.assertDoesNotThrow(() -> userService.registerUser(registrationBody),
                "User Should Register Successfully");
        Assertions.assertEquals(registrationBody.getEmail(),greenMailExtension.getReceivedMessages()[0]
                .getRecipients(Message.RecipientType.TO)[0].toString());

    }

}

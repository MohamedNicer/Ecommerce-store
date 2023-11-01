package com.ecs.ecommercestore.Service;

import com.ecs.ecommercestore.Api.Model.LoginBody;
import com.ecs.ecommercestore.Api.Model.RegistrationBody;
import com.ecs.ecommercestore.Entity.VerificationToken;
import com.ecs.ecommercestore.Exception.EmailFailureException;
import com.ecs.ecommercestore.Exception.UserAlreadyExistsException;
import com.ecs.ecommercestore.Exception.UserNotVerifiedException;
import com.ecs.ecommercestore.Repository.VerificationTokenRepository;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @RegisterExtension
    private static GreenMailExtension greenMailExtension = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("Mohamed","secret1"))
            .withPerMethodLifecycle(true);

    @Autowired
    private UserService userService;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

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

    @Test
    @Transactional
    public void testLoginUSer() throws EmailFailureException, UserNotVerifiedException {
        LoginBody body = new LoginBody();
        body.setUsername("User1-NotExists");
        body.setPassword("password11-BadPassword");
        Assertions.assertNull(userService.loginUser(body), "The User Should Not exist");
        body.setUsername("User1");
        Assertions.assertNull(userService.loginUser(body), "The Password Should be incorrect");
        body.setPassword("password11");
        Assertions.assertNotNull(userService.loginUser(body), "The USer Should Login successfully");
        body.setUsername("User2");
        body.setPassword("password21");
        try {
            userService.loginUser(body);
            Assertions.assertTrue(false, "User Should not have email verified");
        }catch (UserNotVerifiedException ex) {
            Assertions.assertTrue(ex.isNewEmailSent(), "Email verification should be sent");
            Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
        }
        try {
            userService.loginUser(body);
            Assertions.assertTrue(false, "User Should not have email verified");
        }catch (UserNotVerifiedException ex) {
            Assertions.assertFalse(ex.isNewEmailSent(), "Email verification should not be resent");
            Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
        }
    }
    @Test
    @Transactional
    public void testVerifyUser() throws EmailFailureException {
        Assertions.assertFalse(userService.verifyUser("Bad Token"),"Token Should Be Invalid or Doesn't Exist");
        LoginBody loginBody = new LoginBody();
        loginBody.setUsername("User2");
        loginBody.setPassword("password21");
        try{
            userService.loginUser(loginBody);
            Assertions.fail("User Should Have His Email Unverified");
        }catch (UserNotVerifiedException e){
            List<VerificationToken> verificationTokens = verificationTokenRepository.findByUser_IdOrderByIdDesc(2L);
            String token = verificationTokens.get(0).getToken();
            Assertions.assertTrue(userService.verifyUser(token),"Token Should Be Valid!");
            Assertions.assertNotNull(loginBody,"User Should Be Verified");
        }
    }
}

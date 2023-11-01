package com.ecs.ecommercestore.Service;

import com.ecs.ecommercestore.Api.Model.LoginBody;
import com.ecs.ecommercestore.Api.Model.PasswordResetBody;
import com.ecs.ecommercestore.Api.Model.RegistrationBody;
import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Entity.VerificationToken;
import com.ecs.ecommercestore.Exception.EmailFailureException;
import com.ecs.ecommercestore.Exception.EmailNotFoundException;
import com.ecs.ecommercestore.Exception.UserAlreadyExistsException;
import com.ecs.ecommercestore.Exception.UserNotVerifiedException;
import com.ecs.ecommercestore.Repository.LocalUserRepository;
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

/**
 * Test class to unit test the UserService class.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    /** Extension for mocking email sending. */
    @RegisterExtension
    private static GreenMailExtension greenMailExtension = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("Mohamed","secret1"))
            .withPerMethodLifecycle(true);

    /** The UserService to test. */
    @Autowired
    private UserService userService;

    /** The JWT Service. */
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    /** The LocalUser Repository. */
    @Autowired
    private LocalUserRepository localUserRepository;

    /** The encryption Service. */
    @Autowired
    private EncryptionService encryptionService;

    /** The Verification Token Repository. */
    @Autowired
    private JWTService jwtService;

    /**
     * Tests the registration process of the user.
     * @throws MessagingException Thrown if the mocked email service fails somehow.
     */
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

    /**
     * Tests the loginUser method.
     * @throws EmailFailureException
     * @throws UserNotVerifiedException
     */
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

    /**
     * Tests the verifyUser method.
     * @throws EmailFailureException
     */
    @Test
    @Transactional
    public void testVerifyUser() throws EmailFailureException {
        Assertions.assertFalse(userService.verifyUser("Bad Token"),
                "Token Should Be Invalid or Doesn't Exist");
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

    /**
     * Tests the forgotPassword method in the User Service.
     * @throws MessagingException
     */
    @Test
    @Transactional
    public void testForgotPassword() throws MessagingException {
        Assertions.assertThrows(EmailNotFoundException.class,() ->
                userService.forgotPassword("fakeUser@mail.com"),"Non Existent Email Should Be Rejected");
        Assertions.assertDoesNotThrow(()-> userService.forgotPassword("user1@mail.com"));
        Assertions.assertEquals("user1@mail.com", greenMailExtension.getReceivedMessages()[0]
                        .getRecipients(Message.RecipientType.TO)[0].toString()
                ,"Reset Password Email Should Be Sent");
    }

    /**
     * Tests the resetPassword method in the User Service.
     * @throws MessagingException
     */
    @Test
    @Transactional
    public void testResetPassword(){
        LocalUser user = localUserRepository.findUserByUsernameIgnoreCase("User1").get();
        String token = jwtService.generatePasswordResetJWT(user);
        PasswordResetBody passwordResetBody = new PasswordResetBody();
        passwordResetBody.setPassword("password123");
        passwordResetBody.setToken(token);
        userService.resetPassword(passwordResetBody);
        user = localUserRepository.findUserByUsernameIgnoreCase("user1").get();
        Assertions.assertTrue(encryptionService.verifyPassword("password123",user.getPassword()),
                "Password Should Be Updated On the DB");
    }
}

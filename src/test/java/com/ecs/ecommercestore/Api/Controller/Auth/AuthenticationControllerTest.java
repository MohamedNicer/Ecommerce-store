package com.ecs.ecommercestore.Api.Controller.Auth;

import com.ecs.ecommercestore.Api.Model.PasswordResetBody;
import com.ecs.ecommercestore.Api.Model.RegistrationBody;
import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Repository.LocalUserRepository;
import com.ecs.ecommercestore.Service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests the endpoints in the AuthenticationController.
 * @author mohamednicer
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    /** Extension for mocking email sending. */
    @RegisterExtension
    private static GreenMailExtension greenMailExtension = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("Mohamed","secret1"))
            .withPerMethodLifecycle(true);

    /** The Mocked MVC. */
    @Autowired
    private MockMvc mockMvc;

    /** The LocalUserRepository */
    @Autowired
    private LocalUserRepository localUserRepository;

    /** The JWTService*/
    @Autowired
    private JWTService jwtService;

    /**
     * Tests the register endpoint.
     * @throws Exception
     */
    @Test
    @Transactional
    public void testRegister() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RegistrationBody registrationBody = new RegistrationBody();
        registrationBody.setUsername(null);
        registrationBody.setEmail("AuthenticationControllerTest$testRegister@mail.com");
        registrationBody.setFirstName("FirstName");
        registrationBody.setLastName("LastName");
        registrationBody.setPassword("password1");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registrationBody.setUsername("");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registrationBody.setUsername("Short");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registrationBody.setUsername("AuthenticationControllerTest$testRegister");
        registrationBody.setEmail(null);
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registrationBody.setEmail("");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registrationBody.setEmail("Wrong_Mail");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registrationBody.setEmail("AuthenticationControllerTest$testRegister@mail.com");
        registrationBody.setPassword(null);
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registrationBody.setPassword("");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registrationBody.setPassword("password");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registrationBody.setPassword("AuthenticationControllerTest$testRegister");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registrationBody.setPassword("password1");
        registrationBody.setFirstName(null);
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registrationBody.setFirstName("");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registrationBody.setFirstName("FirstName");
        registrationBody.setLastName(null);
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registrationBody.setLastName("");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registrationBody.setLastName("LastName");
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registrationBody)))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    /**
     * Tests the reset Password endpoint.
     * @throws Exception
     */
    @Test
    @Transactional
    public void testResetPassword() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        PasswordResetBody passwordResetBody = new PasswordResetBody();
        LocalUser user = localUserRepository.findUserByUsernameIgnoreCase("User1").get();
        String token = jwtService.generatePasswordResetJWT(user);
        passwordResetBody.setToken(token);
        passwordResetBody.setPassword("");
        mockMvc.perform(post("/auth/reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(passwordResetBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        passwordResetBody.setPassword(null);
        mockMvc.perform(post("/auth/reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(passwordResetBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        passwordResetBody.setPassword("Short");
        mockMvc.perform(post("/auth/reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(passwordResetBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        passwordResetBody.setPassword("password11");
        passwordResetBody.setToken(null);
        mockMvc.perform(post("/auth/reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(passwordResetBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        passwordResetBody.setToken("");
        mockMvc.perform(post("/auth/reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(passwordResetBody)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        passwordResetBody.setToken(token);
        mockMvc.perform(post("/auth/reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(passwordResetBody)))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

}

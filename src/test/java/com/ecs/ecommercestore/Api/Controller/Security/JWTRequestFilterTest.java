package com.ecs.ecommercestore.Api.Controller.Security;

import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Repository.LocalUserRepository;
import com.ecs.ecommercestore.Service.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JWTRequestFilterTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private LocalUserRepository localUserRepository;
    private static final String AUTHENTICATED_PATH = "/auth/fetch";

    @Test
    public void testUnauthenticatedRequest() throws Exception {
        mockMvc.perform(get(AUTHENTICATED_PATH)).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }
    @Test
    public void testBadToken() throws Exception {
        mockMvc.perform(get(AUTHENTICATED_PATH).header("Authorization","BadAndInvalidToken"))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
        mockMvc.perform(get(AUTHENTICATED_PATH).header("Authorization","Bearer BadAndInvalidToken"))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }
    @Test
    public void testUnverifiedUser() throws Exception {
        LocalUser user = localUserRepository.findUserByUsernameIgnoreCase("User2").get();
        String token = jwtService.generateJWT(user);
        mockMvc.perform(get(AUTHENTICATED_PATH).header("Authorization","Bearer " + token))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }
    @Test
    public void testVerifiedUser() throws Exception {
        LocalUser user = localUserRepository.findUserByUsernameIgnoreCase("User1").get();
        String token = jwtService.generateJWT(user);
        mockMvc.perform(get(AUTHENTICATED_PATH).header("Authorization","Bearer " + token))
                .andExpect(status().is(HttpStatus.OK.value()));
    }
}

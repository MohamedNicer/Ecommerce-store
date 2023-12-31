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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class for testing the JWTRequestFilter.
 * @author mohamednicer
 */
@SpringBootTest
@AutoConfigureMockMvc
public class JWTRequestFilterTest {

    /** Mocked MVC. */
    @Autowired
    private MockMvc mockMvc;

    /** The JWT Service. */
    @Autowired
    private JWTService jwtService;

    /** The LocalUser Repository. */
    @Autowired
    private LocalUserRepository localUserRepository;

    /** The path that should only allow authenticated users. */
    private static final String AUTHENTICATED_PATH = "/auth/fetch";

    /**
     * Tests that unauthenticated requests are rejected.
     * @throws Exception
     */
    @Test
    public void testUnauthenticatedRequest() throws Exception {
        mockMvc.perform(get(AUTHENTICATED_PATH)).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    /**
     * Tests that bad tokens are rejected.
     * @throws Exception
     */
    @Test
    public void testBadToken() throws Exception {
        mockMvc.perform(get(AUTHENTICATED_PATH).header("Authorization","BadAndInvalidToken"))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
        mockMvc.perform(get(AUTHENTICATED_PATH).header("Authorization","Bearer BadAndInvalidToken"))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    /**
     * Tests unverified users who somehow get a jwt are rejected.
     * @throws Exception
     */
    @Test
    public void testUnverifiedUser() throws Exception {
        LocalUser user = localUserRepository.findUserByUsernameIgnoreCase("User2").get();
        String token = jwtService.generateJWT(user);
        mockMvc.perform(get(AUTHENTICATED_PATH).header("Authorization","Bearer " + token))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    /**
     * Tests the successful authentication.
     * @throws Exception
     */
    @Test
    public void testVerifiedUser() throws Exception {
        LocalUser user = localUserRepository.findUserByUsernameIgnoreCase("User1").get();
        String token = jwtService.generateJWT(user);
        mockMvc.perform(get(AUTHENTICATED_PATH).header("Authorization","Bearer " + token))
                .andExpect(status().is(HttpStatus.OK.value()));
    }
}

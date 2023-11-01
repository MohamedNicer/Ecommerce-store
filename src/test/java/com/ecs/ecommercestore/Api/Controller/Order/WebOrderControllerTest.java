package com.ecs.ecommercestore.Api.Controller.Order;

import com.ecs.ecommercestore.Entity.WebOrder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class for testing OrderController.
 * @author mohamednicer
 */
@SpringBootTest
@AutoConfigureMockMvc
public class WebOrderControllerTest {

    /** Mocked MVC. */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Tests that when requested authenticated order list it belongs to user A.
     * @throws Exception
     */
    @Test
    @WithUserDetails("User1")
    public void testUsersAuthenticatedWebOrdersList() throws Exception {
        testAuthenticatedListBelongsToUser("User1");
    }

    /**
     * Tests that when requested authenticated order list it belongs to user B.
     * @throws Exception
     */
    @Test
    @WithUserDetails("User2")
    public void testUser2AuthenticatedWebOrdersList() throws Exception {
        testAuthenticatedListBelongsToUser("User2");
    }

    /**
     * Tests that when requested authenticated order list it belongs to the given user.
     * @param username the username to test for.
     * @throws Exception
     */
    private void testAuthenticatedListBelongsToUser(String username) throws Exception {
        mockMvc.perform(get("/order"))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    List<WebOrder> orders = new ObjectMapper().readValue(json, new TypeReference<List<WebOrder>>() {});
                    for (WebOrder order: orders) {
                        Assertions.assertEquals(username,order.getUser().getUsername(),
                                "Orders That Shows Up Should Belong To This User");
                    }
                });
    }

    /**
     * Tests the unauthenticated users do not receive data.
     * @throws Exception
     */
    @Test
    public void testUnauthenticatedUserList() throws Exception {
        mockMvc.perform(get("/order")).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }
}

package com.ecs.ecommercestore.Api.Controller.Order;

import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Entity.WebOrder;
import com.ecs.ecommercestore.Service.WebOrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller to handle requests to create, update and view orders.
 * @author mohamednicer
 */
@RestController
@RequestMapping("/order")
public class WebOrderController {

    /** The Order Service. */
    private WebOrderService webOrderService;

    /**
     * Constructor for spring injection.
     * @param webOrderService
     */
    public WebOrderController(WebOrderService webOrderService) {
        this.webOrderService = webOrderService;
    }

    /**
     * Endpoint to get all orders for a specific user.
     * @param user The user provided by spring security context.
     * @return The list of orders the user had made.
     */
    @GetMapping
    public List<WebOrder> getOrders(@AuthenticationPrincipal LocalUser user){
        return webOrderService.getOrders(user);
    }
}

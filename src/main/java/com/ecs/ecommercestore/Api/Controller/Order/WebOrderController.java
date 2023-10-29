package com.ecs.ecommercestore.Api.Controller.Order;

import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Entity.WebOrder;
import com.ecs.ecommercestore.Service.WebOrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/order")
@RestController
public class WebOrderController {
    private WebOrderService webOrderService;

    public WebOrderController(WebOrderService webOrderService) {
        this.webOrderService = webOrderService;
    }
    @GetMapping
    public List<WebOrder> getOrders(@AuthenticationPrincipal LocalUser user){
        return webOrderService.getOrders(user);
    }
}

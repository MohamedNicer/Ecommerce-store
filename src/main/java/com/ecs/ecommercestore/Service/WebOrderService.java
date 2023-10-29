package com.ecs.ecommercestore.Service;

import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Entity.WebOrder;
import com.ecs.ecommercestore.Repository.WebOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebOrderService {
    private WebOrderRepository webOrderRepository;

    public WebOrderService(WebOrderRepository webOrderRepository) {
        this.webOrderRepository = webOrderRepository;
    }
    public List<WebOrder> getOrders(LocalUser user){
        return webOrderRepository.findByUser(user);
    }
}

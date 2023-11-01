package com.ecs.ecommercestore.Service;

import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Entity.WebOrder;
import com.ecs.ecommercestore.Repository.WebOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for handling order actions.
 * @author mohamednicer
 */
@Service
public class WebOrderService {

    /** The Web Order DAO. */
    private WebOrderRepository webOrderRepository;

    /**
     * Constructor for spring injection.
     * @param webOrderRepository
     */
    public WebOrderService(WebOrderRepository webOrderRepository) {
        this.webOrderRepository = webOrderRepository;
    }

    /**
     * Gets the list of orders for a given user.
     * @param user The user to search for.
     * @return The list of orders.
     */
    public List<WebOrder> getOrders(LocalUser user) {
        return webOrderRepository.findByUser(user);
    }

}

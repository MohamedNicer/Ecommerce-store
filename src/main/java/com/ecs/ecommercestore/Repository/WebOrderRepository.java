package com.ecs.ecommercestore.Repository;

import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Entity.WebOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Data Access Object or Repository to access WebOrder data.
 * @author mohamednicer
 */
public interface WebOrderRepository extends JpaRepository<WebOrder,Long> {
    List<WebOrder> findByUser(LocalUser user);
}

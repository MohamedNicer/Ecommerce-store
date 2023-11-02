package com.ecs.ecommercestore.Repository;

import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Entity.WebOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object or Repository to access WebOrder data.
 * @author mohamednicer
 */
@Repository
public interface WebOrderRepository extends JpaRepository<WebOrder,Long> {
    List<WebOrder> findByUser(LocalUser user);
}

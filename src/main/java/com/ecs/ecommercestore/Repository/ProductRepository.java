package com.ecs.ecommercestore.Repository;

import com.ecs.ecommercestore.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access Object or Repository for accessing Product data.
 * @author mohamednicer
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}

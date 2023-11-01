package com.ecs.ecommercestore.Repository;

import com.ecs.ecommercestore.Entity.Product;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data Access Object or Repository for accessing Product data.
 * @author mohamednicer
 */
public interface ProductRepository extends JpaRepository<Product,Long> {
}

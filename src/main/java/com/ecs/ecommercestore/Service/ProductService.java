package com.ecs.ecommercestore.Service;

import com.ecs.ecommercestore.Entity.Product;
import com.ecs.ecommercestore.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for handling product actions.
 * @author mohamednicer
 */
@Service
public class ProductService {

    /** The Product DAO. */
    private ProductRepository productRepository;

    /**
     * Constructor for spring injection.
     * @param productRepository
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Gets the all products available.
     * @return The list of products.
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}

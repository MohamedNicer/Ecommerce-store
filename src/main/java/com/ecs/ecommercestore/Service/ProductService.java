package com.ecs.ecommercestore.Service;

import com.ecs.ecommercestore.Entity.Product;
import com.ecs.ecommercestore.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
}

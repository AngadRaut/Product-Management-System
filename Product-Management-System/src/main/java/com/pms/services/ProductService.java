package com.pms.services;

import com.pms.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public Product saveProduct(Product product);
    public Optional<Product> findProductById(Long productId);
    public Product findProductByProductName(String productName);
    public Product updateProduct(Product product,Long productId);
    public List<Product> findAllProduct();
    public void deleteProductById(Long productId);
}

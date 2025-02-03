package com.pms.services.impl;

import com.pms.repository.ProductDetailsRepository;
import com.pms.entities.Product;
import com.pms.custom_exceptions.AlreadyExistsException;
import com.pms.repository.ProductRepository;
import com.pms.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Override
    public Product saveProduct(Product product) {
        Optional<Product> byId = this.productRepository.findById(product.getProductId());
        if(byId.isPresent()){
            throw new AlreadyExistsException(byId.get()+" This category is already present in the records!");
        }else {
            return this.productRepository.save(product);
        }
    }

    @Override
    public Optional<Product> findProductById(Long productId) {
        return Optional.empty();
    }

    @Override
    public Product findProductByProductName(String productName) {
        return null;
    }

    @Override
    public Product updateProduct(Product product, Long productId) {
        return null;
    }

    @Override
    public List<Product> findAllProduct() {
        return List.of();
    }

    @Override
    public void deleteProductById(Long productId) {

    }
}

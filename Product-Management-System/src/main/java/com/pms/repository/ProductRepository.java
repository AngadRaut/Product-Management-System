package com.pms.repository;

import com.pms.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    public Optional<Product> findProductByProductName(String productName);
}

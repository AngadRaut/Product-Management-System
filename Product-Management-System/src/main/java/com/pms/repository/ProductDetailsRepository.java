package com.pms.repository;

import com.pms.dto.ProductResponse;
import com.pms.entities.Product;
import com.pms.models.ProductDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDetailsRepository extends MongoRepository<ProductDetails,Long> {
    public Optional<ProductDetails> findProductDetailsByProductId(Long productId);
    public void deleteProjectDetailsByProductId(Long productId);
//    public Optional<ProductDetails> findProductDetailsByProductDetailsId(String productDetailsId);
}

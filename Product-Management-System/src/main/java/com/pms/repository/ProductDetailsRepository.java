package com.pms.repository;

import com.pms.models.ProductDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailsRepository extends MongoRepository<ProductDetails,Long> {

}

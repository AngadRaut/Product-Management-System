package com.pms.services.impl;

import com.pms.custom_exceptions.AlreadyExistsException;
import com.pms.models.ProductDetails;
import com.pms.repository.ProductDetailsRepository;
import com.pms.services.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {

    @Autowired
    private ProductDetailsRepository productDetailsRepository;
    @Override
    public ProductDetails saveProductDetails(ProductDetails productDetails) {
        Optional<ProductDetails> byId = this.productDetailsRepository.findById(productDetails.getProductId());
        if(byId.isPresent()){
            throw new AlreadyExistsException(byId.get()+" This category is already present in the records!");
        }else {
            return this.productDetailsRepository.save(productDetails);
        }
    }

    @Override
    public Optional<ProductDetails> findProductDetailsById(String productDetailsId) {
        return Optional.empty();
    }

    @Override
    public ProductDetails updateProductDetails(ProductDetails productDetails, String productDetailsId) {
        return null;
    }

    @Override
    public List<ProductDetails> findAllProductDetails() {
        return List.of();
    }

    @Override
    public void deleteProductDetailsById(String productDetailsId) {

    }
}

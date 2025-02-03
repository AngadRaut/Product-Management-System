package com.pms.services;

import com.pms.models.ProductDetails;

import java.util.List;
import java.util.Optional;

public interface ProductDetailsService {
    public ProductDetails saveProductDetails(ProductDetails productDetails);
    public Optional<ProductDetails> findProductDetailsById(String productDetailsId);
//    public ProductDetails findCategoryByBankName(String categoryName);
    public ProductDetails updateProductDetails(ProductDetails productDetails,String productDetailsId);
    public List<ProductDetails> findAllProductDetails();
    public void deleteProductDetailsById(String productDetailsId);

}

package com.pms.services;

import com.pms.dto.ProductRequest;
import com.pms.dto.ProductResponse;

import java.util.List;

public interface ProductAndProductDetailsService {
    public String saveProductAndProductDetails(ProductRequest productRequest);
    public List<ProductResponse> findAllProduct();
    public ProductResponse findProductById(Long productId);
    public void deleteProductById(Long productId);
    public void updateProductById(Long productId, ProductRequest productRequest);
}

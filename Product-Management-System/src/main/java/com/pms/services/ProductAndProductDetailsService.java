package com.pms.services;

import com.pms.dto.ProductRequest;
import com.pms.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductAndProductDetailsService {
    public String saveProductAndProductDetails(ProductRequest productRequest);
    public List<ProductResponse> findAllProduct();
    public ProductResponse findProductById(Long productId);
    public ProductResponse findProductByProductName(String productName);
    public void deleteProductById(Long productId);
    public void updateProductById(Long productId, ProductRequest productRequest);
    public List<ProductResponse> findProductUsingPagination(int pageNo,int pageSize,String sortBy,String direction);
}

package com.pms.services.impl;

import com.pms.dto.ProductRequest;
import com.pms.models.ProductDetails;
import com.pms.repository.ProductDetailsRepository;
import com.pms.entities.Product;
import com.pms.repository.ProductRepository;
import com.pms.services.ProductAndProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductAndProductDetailsServiceImpl implements ProductAndProductDetailsService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailsRepository productDetailsRepository;


    @Override
    public String saveProductAndProductDetails(ProductRequest productRequest) {
        // saving the product
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setBrandName(productRequest.getBrandName());
        product.setPrice(productRequest.getPrice());
        product.setMadeIn(productRequest.getMadeIn());
        product.setStock(productRequest.getStock());
        product.setWarrantyDetails(productRequest.getWarrantyDetails());
        product.setSeller(productRequest.getSeller());
        product.setCategory(productRequest.getCategory());
        Product product1 = this.productRepository.save(product);

        // productDetails save
        ProductDetails productDetails = new ProductDetails();
        productDetails.setDescription(productRequest.getDescription());
        productDetails.setImages(productRequest.getImages());
        productDetails.setSpecifications(productRequest.getSpecifications());
        productDetails.setUsageInstructions(productRequest.getUsageInstructions());
        productDetails.setCustomerFAQ(productRequest.getCustomerFAQ());
        productDetails.setMaterialType(productRequest.getMaterialType());
        productDetails.setWarrantyInfo(productRequest.getWarrantyInfo());
        productDetails.setCountryOfOrigin(productRequest.getCountryOfOrigin());
        productDetails.setSizes(productRequest.getSizes());
        productDetails.setHighlights(productRequest.getHighlights());
        productDetails.setFeatures(productRequest.getFeatures());
        productDetails.setQuantity(productRequest.getQuantity());
        productDetails.setProductId(product1.getProductId());
        ProductDetails productDetails1 = this.productDetailsRepository.save(productDetails);
        return "Product and Product Details created successfully: Product ID - " + product.getProductId() + ", Product Details ID - " + productDetails1.getProductDetailsId();
    }
}

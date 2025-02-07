package com.pms.services.impl;

import com.pms.custom_exceptions.ResourceNotFoundException;
import com.pms.dto.ProductRequest;
import com.pms.dto.ProductResponse;
import com.pms.models.ProductDetails;
import com.pms.repository.ProductDetailsRepository;
import com.pms.entities.Product;
import com.pms.repository.ProductRepository;
import com.pms.services.ProductAndProductDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductAndProductDetailsServiceImpl implements ProductAndProductDetailsService {
    private static final Logger log = LoggerFactory.getLogger(ProductAndProductDetailsServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Override
    public String saveProductAndProductDetails(ProductRequest productRequest) {
        log.info("Received request to add product!");
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

        log.debug("Saving product with name: {} and brand: {}", product.getProductName(), product.getBrandName());
        Product product1 = this.productRepository.save(product);
        log.info("Product saved successfully: Product ID - {}", product.getProductId());

        // productDetails save
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductDetailsId(productRequest.getProductDetailsId());
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
        log.debug("Saving product details with name: {}",productDetails.getProductDetailsId());
        ProductDetails productDetails1 = this.productDetailsRepository.save(productDetails);
        log.info("ProductDetails saved successfully: ProductDetails ID - {}", productDetails.getProductDetailsId());
        return "Product and Product Details created successfully: Product ID - " + product.getProductId() + ", Product Details ID - " + productDetails1.getProductDetailsId();
    }

    @Override
    public List<ProductResponse> findAllProduct() {
        log.info("Received request to find all products.");
        // find all products
        List<Product> products = this.productRepository.findAll();
        log.debug("Found {} products.", products.size());

        // find all productDetails
        List<ProductDetails> productDetails = this.productDetailsRepository.findAll();
        log.debug("Found {} product details.", productDetails.size());

        List<ProductResponse> productResponses = products.stream().map(product -> {
            return productDetails.stream()
                    .filter(details -> details.getProductId().equals(product.getProductId()))
                    .findFirst()
                    .map(details -> new ProductResponse(product, details))
                    .orElse(null);
        }).filter(Objects::nonNull).toList();

        log.info("Returning {} product responses.", productResponses.size());
        return productResponses;
    }

    @Override
    public ProductResponse findProductById(Long productId) {
        log.info("Received request to find product by ID: {}", productId);

        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Product not found for ID: {}", productId);
                    return new ResourceNotFoundException("Product with id " + productId + " is not present in the records.");
                });

        ProductDetails productDetails = this.productDetailsRepository.findProductDetailsByProductId(productId)
                .orElseThrow(() -> {
                    log.warn("Product details not found for ID: {}", productId);
                    return new ResourceNotFoundException("Product details for product id " + productId + " are not present in the records.");
                });

        log.info("Product found with ID: {}. Returning product details.", productId);
        return new ProductResponse(product, productDetails);
    }

    @Override
    public ProductResponse findProductByProductName(String productName) {
        log.info("Received request to find product by productName : {}", productName);

        Product product = this.productRepository.findProductByProductName(productName)
                .orElseThrow(() -> {
                    log.warn("Product not found for name : {}", productName);
                    return new ResourceNotFoundException("Product with name " + productName + " is not present in the records.");
                });

        Long productId = product.getProductId();
        ProductDetails productDetails = this.productDetailsRepository.findProductDetailsByProductId(productId)
                .orElseThrow(() -> {
                    log.warn("Product details not found for product name : {}", productName);
                    return new ResourceNotFoundException("ProductDetails for product product name  " + productName + " are not present in the records.");
                });

        log.info("Product found with name : {}. Returning product details.", productName);
        return new ProductResponse(product, productDetails);
    }

    @Override
    public void deleteProductById(Long productId) {
        log.info("Received request to delete product by ID: {}", productId);
        Optional<Product> byId = this.productRepository.findById(productId);
        Optional<ProductDetails> productDetailsByProductId = this.productDetailsRepository.findProductDetailsByProductId(productId);
        if(byId.isEmpty() || productDetailsByProductId.isEmpty()){
            log.warn("Product or product details not found for ID: {}", productId);
            throw new ResourceNotFoundException("Product having id "+productId+" is not present in the records.");
        }else {
            log.info("Deleting product with ID: {}", productId);
            this.productRepository.deleteById(productId);
            this.productDetailsRepository.deleteProjectDetailsByProductId(productId);
            log.info("Product with ID: {} and corresponding product details deleted successfully.", productId);
        }
    }

    @Override
    public void updateProductById(Long productId, ProductRequest productRequest) {
        log.info("Received request to update product : {}", productId);
        Optional<Product> productById = this.productRepository.findById(productId);

        if(productById.isEmpty()){
            log.warn("Product not found for ID: {}", productId);
            throw new ResourceNotFoundException("Product with id " + productId + " is not present in the records.");
        }

        //  update product -> postgresql entity
        Product product = productById.get();
        product.setProductId(productId);
        product.setProductName(productRequest.getProductName());
        product.setBrandName(productRequest.getBrandName());
        product.setPrice(productRequest.getPrice());
        product.setWarrantyDetails(productRequest.getWarrantyDetails());
        product.setStock(productRequest.getStock());
        product.setSeller(productRequest.getSeller());
        product.setCategory(productRequest.getCategory());
        product.setMadeIn(productRequest.getMadeIn());

        log.debug("Updating product with name: {} and brand: {}", product.getProductName(), product.getBrandName());
        this.productRepository.save(product);
        log.info("Product updated successfully.");

        // update productDetails -> mongo db entity
        // Ensure that findProductDetailsByProductId() is correctly fetching the existing ProductDetails.
        ProductDetails productDetails = this.productDetailsRepository.findProductDetailsByProductId(productId)
                .orElseThrow(() -> {
                    log.warn("ProductDetails not found for productId: {}", productId);
                    return new ResourceNotFoundException("ProductDetails not found for productId " + productId);
                });

        log.info("productDetails{}:",productDetails);

        // Check If productDetailsId Exists Before Updating
        String productDetailsId = productDetails.getProductDetailsId();

        // verify that productDetailsId is not null before using it
        if (productDetailsId == null) {
            log.error("ProductDetailsId is null for productId: {}", productId);
            throw new IllegalStateException("ProductDetailsId is null for productId: " + productId);
        }

        // if ProductDetails primary key is not null then print it through log
        log.info("ProductDetails primary key  productDetailsId - {} : ",productId);
        productDetailsRepository.deleteProjectDetailsByProductId(productId);

        productDetails.setProductDetailsId(productDetailsId);
        productDetails.setProductId(productId);
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

        log.debug("Updating productDetails with productId: {} ", productDetails.getProductId());
        ProductDetails productDetails1 = this.productDetailsRepository.save(productDetails);
        log.info("ProductDetails updated successfully.");
        log.info("Product and ProductDetails updated successfully!");
    }
}
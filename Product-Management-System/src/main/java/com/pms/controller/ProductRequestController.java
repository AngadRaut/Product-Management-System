package com.pms.controller;

import com.pms.dto.ProductRequest;
import com.pms.dto.ProductResponse;
import com.pms.services.ProductAndProductDetailsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductRequestController {
    private static final Logger log = LoggerFactory.getLogger(ProductRequestController.class);

    private ProductAndProductDetailsService service;

    @Autowired
    public ProductRequestController(ProductAndProductDetailsService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody ProductRequest productRequest, BindingResult result){
        log.info("Received request to add product.");
        // Validate the input
        if (result.hasErrors()) {
            log.warn("Validation failed for product request:{}",result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Invalid input.", "errors", result.getAllErrors()).toString());
        }
        String string = this.service.saveProductAndProductDetails(productRequest);
        log.info("Product successfully added with response.");
        return ResponseEntity.status(HttpStatus.CREATED).body(string);
    }

    @GetMapping("get/byId/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long productId) {
        log.info("Received request to get product by ID: {}", productId);
        ProductResponse productResponse = this.service.findProductById(productId);
        log.info("Product found with ID {}", productId);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("get/byName/{name}")
    public ResponseEntity<?> getProductByProductName(@PathVariable("name") String productName) {
        log.info("Received request to get product by name : {}", productName);
        ProductResponse productResponse = this.service.findProductByProductName(productName);
        log.info("Product found with name {}", productName);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/delete/byId/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id") Long productId){
        log.info("Received request to delete product with ID: {}", productId);

        this.service.deleteProductById(productId);

        log.info("Product with ID {} deleted successfully.", productId);
        return ResponseEntity.ok(Map.of("message", "Product deleted successfully", "productId", productId));
    }

    @GetMapping("/getAll/product")
    public ResponseEntity<?> getAllProduct(){
        log.info("Received request to get all product");
        List<ProductResponse> allProduct = this.service.findAllProduct();

        if (allProduct.isEmpty()) {
            log.warn("No product found in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "No Product found."));
        }

        log.info("Product from records  fetched successfully:{}", allProduct.size());
        return ResponseEntity.status(HttpStatus.OK).body(allProduct);
    }
    @PutMapping("/update/byId/{id}")
    public ResponseEntity<?> updateProductAndDetails(@Valid @RequestBody ProductRequest productRequest,BindingResult result,@PathVariable("id") Long productId){
        log.info("Received request to update product: productId {}", productId);

        // Validate the input
        if (result.hasErrors()) {
            log.warn("Validation failed for updating product operation request: {}", result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input\n"+result.getAllErrors());
        }
        this.service.updateProductById(productId,productRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Product updated successfully.");
    }
    // handler method to get product using pagination
    @GetMapping("/getAll/usingPageable")
    public ResponseEntity<List<ProductResponse>> findProductUsingPageable(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(value = "",defaultValue = "",required = false) String sortBy,
            @RequestParam(value = "",defaultValue = "",required = false) String direction
    ){
        log.info("Received request to controller method to find all products using pageable for pageNo={}, pageSize={}, sortBy={}, direction={}", pageNo, pageSize, sortBy, direction);
        List<ProductResponse> productUsingPagination = this.service.findProductUsingPagination(pageNo, pageSize, sortBy, direction);
        return ResponseEntity.status(HttpStatus.OK).body(productUsingPagination);
    }
}
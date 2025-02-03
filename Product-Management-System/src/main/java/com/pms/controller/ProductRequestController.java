package com.pms.controller;

import com.pms.dto.ProductRequest;
import com.pms.services.ProductAndProductDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/productRequest")
public class ProductRequestController {

    @Autowired
    private ProductAndProductDetailsService service;

    @PostMapping("/add")
    public ResponseEntity<String> add(@Valid @RequestBody ProductRequest productRequest, BindingResult result){

        // Validate the input
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
        }
        String string = this.service.saveProductAndProductDetails(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("resource created successfully!\n"+string);
    }
}

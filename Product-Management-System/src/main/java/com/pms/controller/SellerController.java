package com.pms.controller;

import com.pms.entities.Seller;
import com.pms.services.SellerService;
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
@RequestMapping("/api/seller")
public class SellerController {

    @Autowired
    private SellerService service;

    @PostMapping("/add")
    public ResponseEntity<String> addSeller(@Valid @RequestBody Seller seller, BindingResult result){

        // Validate the input
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
        }
        Seller seller1 = this.service.saveSeller(seller);
        return ResponseEntity.status(HttpStatus.CREATED).body("New Seller adder in records!!"+seller1);
    }
}

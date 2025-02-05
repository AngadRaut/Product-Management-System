package com.pms.controller;

import com.pms.entities.Category;
import com.pms.entities.Seller;
import com.pms.services.SellerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    private static final Logger log = LoggerFactory.getLogger(SellerController.class);

    @Autowired
    private SellerService service;

    @PostMapping("/add")
    public ResponseEntity<String> addSeller(@Valid @RequestBody Seller seller, BindingResult result){
        log.info("Received request to add seller : {}", seller);
        // Validate the input
        if (result.hasErrors()) {
            log.warn("Validation failed for saving seller request: {}", result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
        }
        Seller seller1 = this.service.saveSeller(seller);
        log.info("Seller successfully added in records : {}", seller1);
        return ResponseEntity.status(HttpStatus.CREATED).body("New Seller adder in records!!"+seller1);
    }
    // find product by id
    @GetMapping("/get/byId/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable("id") Long sellerId) {
        log.info("Received request to get category by ID: {}", sellerId);
        Seller category = service.findSellerById(sellerId);
        log.info("Seller with ID: {} found successfully!", sellerId);
        return ResponseEntity.ok(category);
    }

    // get all seller
    @GetMapping("/get/all")
    public ResponseEntity<String> getAllSellers() {
        log.info("Received request to fetch all sellers.");
        List<Seller> sellers = service.findAllSeller();

        if (sellers.isEmpty()) {
            log.warn("No sellers found in the database.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No sellers found in the database.");
        }

        log.info("Sellers retrieved successfully. Total sellers: {}", sellers.size());
        return ResponseEntity.status(HttpStatus.OK).body("Sellers retrieved successfully. Total sellers:"+sellers.size());
    }

    // delete seller by id
    @DeleteMapping("/delete/byId/{id}")
    public ResponseEntity<String> deleteSellerById(@PathVariable("id") Long sellerId) {
        log.info("Received request to delete seller with ID: {}", sellerId);
        service.deleteSellerById(sellerId);
        log.info("Seller with ID: {} deleted successfully.", sellerId);
        return ResponseEntity.status(HttpStatus.OK).body("Seller with ID " + sellerId + " deleted successfully.");
    }
}

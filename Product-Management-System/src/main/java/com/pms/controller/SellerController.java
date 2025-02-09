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
import java.util.Map;

@RestController
@RequestMapping("/api/seller")
public class SellerController {
    private static final Logger log = LoggerFactory.getLogger(SellerController.class);

    @Autowired
    private SellerService service;

    @PostMapping("/add")
    public ResponseEntity<String> addSeller(@Valid @RequestBody Seller seller, BindingResult result){
        log.info("Received request to add seller.");
        // Validate the input
        if (result.hasErrors()) {
            log.warn("Validation failed for saving seller request: {}", result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Invalid input.", "errors", result.getAllErrors()).toString());
        }
        Seller seller1 = this.service.saveSeller(seller);
        log.info("Seller successfully added in records sellerId : {}",seller1.getSellerId());
        return ResponseEntity.status(HttpStatus.CREATED).body("Seller added successfully, sellerId:"+seller1.getSellerId());
    }

    // find product by id
    @GetMapping("/get/byId/{id}")
    public ResponseEntity<?> getSellerById(@PathVariable("id") Long sellerId) {
        log.info("Received request to get seller by ID: {}", sellerId);
        Seller sellerById = service.findSellerById(sellerId);
        log.info("Seller with ID: {} found successfully!", sellerId);
        return ResponseEntity.ok(sellerById);
    }

    // find seller by name
    @GetMapping("/get/name/{name}")
    public ResponseEntity<?> findSellerBySellerName(@PathVariable("name")String sellerName){
        log.info("Received request to fetch seller with name: {}", sellerName);
        Seller sellerByName = service.findSellerBySellerName(sellerName);
        log.info("Successfully retrieved seller data of sellerName: {}", sellerName);
        return ResponseEntity.ok(sellerByName);
    }

    @PutMapping("/update/byId/{id}")
    public ResponseEntity<?> updateSeller(@Valid @RequestBody Seller seller
            ,BindingResult result,@PathVariable("id") Long sellerId){
        log.info("Received request to update seller : sellerId {}", sellerId);

        // Validate the input
        if (result.hasErrors()) {
            log.warn("Validation failed for updating seller operation request: {}", result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input\n"+result.getAllErrors());
        }
        this.service.updateSeller(sellerId,seller);
        return ResponseEntity.status(HttpStatus.OK).body("Seller updated successfully.");
    }

    // get all seller
    @GetMapping("/get/all")
    public ResponseEntity<?> getAllSellers() {
        log.info("Received request to fetch all sellers.");
        List<Seller> sellers = service.findAllSeller();

        if (sellers.isEmpty()) {
            log.warn("No sellers found in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "No sellers found."));
        }

        log.info("Sellers retrieved successfully. Total sellers: {}", sellers.size());
        return ResponseEntity.ok(sellers);
    }

    // delete seller by id
    @DeleteMapping("/delete/byId/{id}")
    public ResponseEntity<?> deleteSellerById(@PathVariable("id") Long sellerId) {
        log.info("Received request to delete seller with ID: {}", sellerId);
        service.deleteSellerById(sellerId);
        log.info("Seller with ID: {} deleted successfully.", sellerId);
        return ResponseEntity.ok(Map.of("message", "Seller deleted successfully", "sellerId", sellerId));
    }
}
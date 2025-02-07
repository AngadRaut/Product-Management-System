package com.pms.services.impl;

import ch.qos.logback.core.testUtil.MockInitialContext;
import com.pms.custom_exceptions.ResourceNotFoundException;
import com.pms.entities.Category;
import com.pms.entities.Seller;
import com.pms.repository.SellerRepository;
import com.pms.services.SellerService;
import org.bson.assertions.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static javax.management.Query.times;

@Service
public class SellerServiceImpl implements SellerService {
    private static final Logger log = LoggerFactory.getLogger(SellerServiceImpl.class);

    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public Seller saveSeller(Seller seller) {
        log.warn("Seller for given id saved in records.");
        return this.sellerRepository.save(seller);
    }

    @Override
    public Seller findSellerById(Long sellerId) {
        return sellerRepository.findById(sellerId)
                .orElseThrow(() -> {
                    log.warn("Seller for given id not present in records.");
                    return new ResourceNotFoundException("Seller with id " + sellerId + " is not present in the records.");
                });
    }

    @Override
    public List<Seller> findAllSeller() {
        List<Seller> sellers = sellerRepository.findAll();
        log.info("Fetched sellers from the database.{}", sellers.size());
        return sellers;
    }

    @Override
    public void deleteSellerById(Long sellerId) {

    }

    @Override
    public Seller findSellerBySellerName(String sellerName) {
        log.info("Fetching Seller by name: {}", sellerName);
        Seller seller = sellerRepository.findSellerBySellerName(sellerName)
                .orElseThrow(() -> {
                    log.warn("Seller with name {} not found.", sellerName);
                    return new ResourceNotFoundException("Seller with name " + sellerName + " is not present in the record.");
                });
        log.info("Seller with name {} returned successfully!.", sellerName);
        return seller;
    }

    @Override
    public void updateSeller(Long sellerId, Seller seller) {
        log.info("Update Seller by id: {}", sellerId);
        Seller seller1 = sellerRepository.findById(sellerId)
                .orElseThrow(() -> {
                    log.warn("Seller with id {} not found.", sellerId);
                    return new ResourceNotFoundException("Seller with id " + sellerId + " is not present in the record.");
                });

        seller1.setSellerId(sellerId);
        seller1.setSellerName(seller.getSellerName());
        seller1.setMobileNo(seller.getMobileNo());
        seller1.setEmail(seller.getEmail());
        seller1.setCompanyName(seller.getCompanyName());
        seller1.setAddress1(seller.getAddress1());
        seller1.setAddress2(seller.getAddress2());
        seller1.setProduct(seller.getProduct());

        log.debug("Updating Seller with id: {} and name : {}", seller1.getSellerId(), seller1.getSellerName());

        this.sellerRepository.save(seller1);
        log.info("Seller updated successfully.");
    }
}

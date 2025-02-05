package com.pms.services.impl;

import com.pms.custom_exceptions.ResourceNotFoundException;
import com.pms.entities.Category;
import com.pms.entities.Seller;
import com.pms.repository.SellerRepository;
import com.pms.services.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {
    private static final Logger log = LoggerFactory.getLogger(SellerServiceImpl.class);

    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public Seller saveSeller(Seller seller) {
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
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> {
                    log.warn("Seller with given ID not found.");
                    return new ResourceNotFoundException("Seller with ID " + sellerId + " is not present in the record.");
                });

        sellerRepository.delete(seller);
        log.info("Seller with provided ID deleted successfully.");
    }
}

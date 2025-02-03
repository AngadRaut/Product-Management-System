package com.pms.services.impl;

import com.pms.entities.Seller;
import com.pms.repository.SellerRepository;
import com.pms.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public Seller saveSeller(Seller seller) {
        return this.sellerRepository.save(seller);
    }

    @Override
    public Optional<Seller> findSellerById(Long sellerId) {
        return Optional.empty();
    }

    @Override
    public Seller findSellerBySellerName(String sellerName) {
        return null;
    }

    @Override
    public Seller updateseller(Seller seller, Long sellerId) {
        return null;
    }

    @Override
    public List<Seller> findAllSeller() {
        return List.of();
    }

    @Override
    public void deleteSellerById(Long sellerId) {

    }
}

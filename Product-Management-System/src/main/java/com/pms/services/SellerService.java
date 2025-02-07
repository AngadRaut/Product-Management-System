package com.pms.services;

import com.pms.entities.Category;
import com.pms.entities.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerService {
    public Seller saveSeller(Seller seller);
    public Seller findSellerById(Long sellerId);
    public List<Seller> findAllSeller();
    public void deleteSellerById(Long sellerId);
    public Seller findSellerBySellerName(String sellerName);
    public void updateSeller(Long sellerId,Seller seller);
}

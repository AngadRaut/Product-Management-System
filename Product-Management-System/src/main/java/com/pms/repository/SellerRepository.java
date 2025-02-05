package com.pms.repository;

import com.pms.entities.Category;
import com.pms.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {
    public Optional<Seller> findSellerBySellerName(String sellerName);
}

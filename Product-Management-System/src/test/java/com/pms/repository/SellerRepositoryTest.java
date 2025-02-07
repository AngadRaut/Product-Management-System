package com.pms.repository;

import com.pms.custom_exceptions.ResourceNotFoundException;
import com.pms.entities.Seller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SellerRepositoryTest {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    Seller seller = new Seller(null,"bhanu","7218997215","bhanu@gmail.com","TDIT",true,"address12345","address9089",null);


    @BeforeEach
    public void setUp(){

        testEntityManager.persistAndFlush(seller);
    }

    @Test
    public void findSellerByNameTest(){
        Seller seller1 = this.sellerRepository.findSellerBySellerName("bhanu").get();
        Assertions.assertEquals(seller.getSellerId(),seller1.getSellerId());
        Assertions.assertEquals(seller.getEmail(),seller1.getEmail());

    }
    @Test
    public void findSellerByNameNegativeTest(){
        Assertions.assertThrows(ResourceNotFoundException.class,()->this.sellerRepository.findSellerBySellerName("nana").orElseThrow(()-> new ResourceNotFoundException("not found")));
    }

   /* @Test
    public void saveSellerTest(){
        Seller seller = new Seller();
        seller.setSellerName("nana");
        seller.setMobileNo("7218997215");
        seller.setEmail("nana12@gmail.com");
        seller.setCompanyName("bba company");
        seller.setVerifies(true);
        seller.setAddress1("pune post warje");
        seller.setAddress2("\"haridwar");
        seller.setProduct(null);

        Seller save = sellerRepository.save(seller);
        Assertions.assertNotNull(save);
        Assertions.assertNotNull(save.getSellerId());
    }
    @Test
    public void findAllSeller(){
        Seller seller = new Seller();
        seller.setSellerName("nana");
        seller.setMobileNo("7218997215");
        seller.setEmail("nana12@gmail.com");
        seller.setCompanyName("bba company");
        seller.setVerifies(true);
        seller.setAddress1("pune post warje");
        seller.setAddress2("\"haridwar");
        seller.setProduct(null);

        Seller seller2 = new Seller();
        seller2.setSellerName("bhanu");
        seller2.setMobileNo("9218997215");
        seller2.setEmail("bhau12@gmail.com");
        seller2.setCompanyName("TTT company");
        seller2.setVerifies(true);
        seller2.setAddress1(" post warje");
        seller2.setAddress2("\"ramnagar");
        seller2.setProduct(null);

        int count = sellerRepository.findAll().size();
        sellerRepository.save(seller);
        sellerRepository.save(seller2);

        List<Seller> findSeller = sellerRepository.findAll();
        Assertions.assertNotNull(findSeller);
        Assertions.assertEquals((count+2),findSeller.size());
    }*/
}
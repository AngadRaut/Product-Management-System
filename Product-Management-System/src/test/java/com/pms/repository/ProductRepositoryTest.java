package com.pms.repository;

import com.pms.custom_exceptions.ResourceNotFoundException;
import com.pms.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    Product product = new Product(null,"laptop","dell",800.00,"2 years","india",100,null,null);

    @BeforeEach
    public void setUp(){
        productRepository.save(product);
    }

    @Test
    public void findProductByProductNameTest(){
        Product product1 = this.productRepository.findProductByProductName("laptop").get();

        Assertions.assertEquals(product.getProductId(),product1.getProductId());
        Assertions.assertEquals(product.getBrandName(),product1.getBrandName());
        Assertions.assertEquals(product.getPrice(),product1.getPrice());
        Assertions.assertEquals(product.getWarrantyDetails(),product1.getWarrantyDetails());
        Assertions.assertEquals(product.getMadeIn(),product1.getMadeIn());
        Assertions.assertEquals(product.getStock(),product1.getStock());
        Assertions.assertEquals(product.getSeller(),product1.getSeller());
        Assertions.assertEquals(product.getCategory(),product1.getCategory());
    }

    @Test
    public void findProductByProductNameTest_notFound(){
        Assertions.assertThrows(ResourceNotFoundException.class,
                ()->this.productRepository.findProductByProductName("nn")
                        .orElseThrow(()->new ResourceNotFoundException("not found")));
    }
}

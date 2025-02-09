package com.pms.repository;

import com.pms.configuration.MongoDbConfiguration;
import com.pms.custom_exceptions.ResourceNotFoundException;
import com.pms.models.ProductDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;

@DataMongoTest
@ContextConfiguration(classes = MongoDbConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductDetailsRepositoryTest {
    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    ProductDetails productDetails;

    @BeforeEach
    public void setUp(){
        Map<String, String> specifications = new HashMap<>();
        specifications.put("Color", "Red");
        specifications.put("Weight", "500g");
        // Creating a list for customer FAQ
        List<Map<String, String>> customerFAQ = Arrays.asList(
                Map.of("question", "How to use?", "answer", "Follow the manual."),
                Map.of("question", "Is it washable?", "answer", "Yes, it is machine washable.")
        );

        List<String> sizes = Arrays.asList("S", "M", "L");
        List<String> highlights = Arrays.asList("Lightweight", "Breathable");
        List<String> features = Arrays.asList("Durable", "Eco-friendly");

        // Creating a ProductDetails object using the constructor
        productDetails = new ProductDetails(
                "PD123123",                         // productDetailsId
                100L,                            // productId
                "This is a high-quality red shirt.", // description
                null,              // images (example byte array)
                specifications,                   // specifications
                "Machine wash in cold water.",    // usageInstructions
                customerFAQ,                      // customerFAQ
                "Cotton",                         // materialType
                "1-year warranty.",               // warrantyInfo
                "Made in India",                  // countryOfOrigin
                null,                            // sizes
                highlights,                       // highlights
                features,                         // features
                50                                // quantity
        );

        productDetailsRepository.save(productDetails);
    }

   /* @Test
    public void findProductDetailsByProductIdTest(){
        ProductDetails productDetails1 = this.productDetailsRepository.findProductDetailsByProductId(100L).get();
        Assertions.assertEquals(productDetails.getProductDetailsId(),productDetails1.getProductDetailsId());
        Assertions.assertEquals(productDetails.getDescription(),productDetails1.getDescription());
        Assertions.assertEquals(productDetails.getImages(),productDetails1.getImages());
        Assertions.assertEquals(productDetails.getMaterialType(),productDetails1.getMaterialType());
        Assertions.assertEquals(productDetails.getQuantity(),productDetails1.getQuantity());
        Assertions.assertEquals(productDetails.getCountryOfOrigin(),productDetails1.getCountryOfOrigin());
    }*/
    @Test
    public void findProductDetailsByProductId_NotFoundTest(){
        Assertions.assertThrows(ResourceNotFoundException.class,
                ()->productDetailsRepository.findProductDetailsByProductId(10L)
                        .orElseThrow(()->new ResourceNotFoundException("not found")));
    }
/*    @Test
    public void deleteProjectDetailsByProductIdTest(){
        Optional<ProductDetails> beforeDelete = productDetailsRepository.findProductDetailsByProductId(100L);
        Assertions.assertTrue(beforeDelete.isPresent(), "Product should exist before deletion");

        productDetailsRepository.deleteProjectDetailsByProductId(100L);

        // Verify delete
        Optional<ProductDetails> afterDelete = productDetailsRepository.findProductDetailsByProductId(100L);
        Assertions.assertFalse(afterDelete.isPresent(), "Product should be deleted");

    }*/
}
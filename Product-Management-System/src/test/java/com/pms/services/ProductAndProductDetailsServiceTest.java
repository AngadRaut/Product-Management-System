package com.pms.services;

import com.pms.dto.ProductRequest;
import com.pms.dto.ProductResponse;
import com.pms.entities.Product;
import com.pms.models.ProductDetails;
import com.pms.repository.ProductDetailsRepository;
import com.pms.repository.ProductRepository;
import com.pms.services.impl.ProductAndProductDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@SpringBootTest
public class ProductAndProductDetailsServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductDetailsRepository productDetailsRepository;

    @InjectMocks
    private ProductAndProductDetailsServiceImpl service;

    private ProductRequest productRequest;
    private Product mockProduct;
    private ProductDetails mockProductDetails;

    @BeforeEach
    public void setUp(){
        // Prepare a sample ProductRequest
        MockitoAnnotations.openMocks(this); // Initialize mocks

        // Prepare a sample ProductRequest
        productRequest = new ProductRequest();
        productRequest.setProductName("Washing Machine");
        productRequest.setBrandName("Samsung");
        productRequest.setPrice(499.99);
        productRequest.setMadeIn("South Korea");
        productRequest.setStock(50);
        productRequest.setWarrantyDetails("2 Years Warranty");
        productRequest.setProductDetailsId("PD789");
        productRequest.setDescription("Front-load washing machine");
        productRequest.setSpecifications(Map.of("Capacity", "7kg"));
        productRequest.setCustomerFAQ(List.of(Map.of("Q1", "Does it consume less power?", "A1", "Yes.")));
        productRequest.setMaterialType("Steel");
        productRequest.setWarrantyInfo("2 Years");
        productRequest.setCountryOfOrigin("South Korea");
        productRequest.setSizes(List.of("Small", "Medium", "Large"));
        productRequest.setHighlights(List.of("Energy Saving", "Fast Wash"));
        productRequest.setFeatures(List.of("Smart Control", "Noise Reduction"));
        productRequest.setQuantity(10);

        // Mock Product object
        mockProduct = new Product();
        mockProduct.setProductId(1L);
        mockProduct.setProductName("Washing Machine");
        mockProduct.setBrandName("Samsung");

        // Mock ProductDetails object
        mockProductDetails = new ProductDetails();
        mockProductDetails.setProductDetailsId("PD789");
        mockProductDetails.setProductId(1L);
        mockProductDetails.setDescription("Front-load washing machine");

        // Mock repository behavior
        Mockito.when(productRepository.save(any(Product.class))).thenReturn(mockProduct);
        Mockito.when(productDetailsRepository.save(any(ProductDetails.class))).thenReturn(mockProductDetails);
    }

    @Test
    public void saveProductAndProductDetailsTest(){
        mockProduct.setProductId(1L);
        mockProductDetails.setProductDetailsId("PD789");
        Mockito.when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            savedProduct.setProductId(1L);
            return savedProduct;
        });

        Mockito.when(productDetailsRepository.save(any(ProductDetails.class))).thenAnswer(invocation -> {
            ProductDetails savedDetails = invocation.getArgument(0);
            savedDetails.setProductDetailsId("PD789");
            return savedDetails;
        });
        String result = service.saveProductAndProductDetails(productRequest);
        Assertions.assertEquals("Product and Product Details created successfully: Product ID - 1, Product Details ID - PD789", result);
        Mockito.verify(productRepository, times(1)).save(any(Product.class));
        Mockito.verify(productDetailsRepository, times(1)).save(any(ProductDetails.class));
    }
    @Test
    public void findAllProductTest() {
        Product product1 = new Product(1L, "Washing Machine", "Samsung", 499.99,
                "2 Years Warranty", "South Korea", 50, null, null);
        Product product2 = new Product(2L, "Smartphone", "Apple", 999.99,
                "1 Year Warranty", "USA", 100, null, null);

        ProductDetails productDetails1 = new ProductDetails("PD789", 1L,
                "Front-load washing machine", null, null, "Use as per manual",
                null, "Steel", "2 Years", "South Korea", null, null, null, 10);
        ProductDetails productDetails2 = new ProductDetails("PD999", 2L,
                "Latest iPhone model", null, null, "Refer Apple guidelines",
                null, "Aluminum", "1 Year", "USA", null, null, null, 15);

        List<Product> products = Arrays.asList(product1, product2);
        List<ProductDetails> productDetails = Arrays.asList(productDetails1, productDetails2);

        Mockito.when(productRepository.findAll()).thenReturn(products);
        Mockito.when(productDetailsRepository.findAll()).thenReturn(productDetails);

        List<ProductResponse> productResponses = service.findAllProduct();

        Assertions.assertNotNull(productResponses);
        Assertions.assertEquals(2, productResponses.size(), "Should return 2 product responses");

        Mockito.verify(productRepository, times(1)).findAll();
        Mockito.verify(productDetailsRepository, times(1)).findAll();
    }
    @Test
    public void findProductByProductNameTest() {
        Mockito.when(productRepository.findProductByProductName("Washing Machine")).thenReturn(Optional.of(mockProduct));
        Mockito.when(productDetailsRepository.findProductDetailsByProductId(1L)).thenReturn(Optional.of(mockProductDetails));

        ProductResponse productResponse = service.findProductByProductName("Washing Machine");

        Assertions.assertNotNull(productResponse, "ProductResponse should not be null");
        Assertions.assertEquals("Washing Machine", productResponse.getProduct().getProductName(), "Product name should be Washing Machine");
        Assertions.assertEquals("PD789", productResponse.getProductDetails().getProductDetailsId(), "ProductDetails ID should be PD789");

        Mockito.verify(productRepository, times(1)).findProductByProductName("Washing Machine");
        Mockito.verify(productDetailsRepository, times(1)).findProductDetailsByProductId(1L);
    }

    @Test
    public void findProductByIdTest() {
        // Mock product and productDetails
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        Mockito.when(productDetailsRepository.findProductDetailsByProductId(1L)).thenReturn(Optional.of(mockProductDetails));

        // Execute method
        ProductResponse productResponse = service.findProductById(1L);

        // Assertions
        Assertions.assertNotNull(productResponse, "ProductResponse should not be null");
        Assertions.assertEquals(1L, productResponse.getProduct().getProductId(), "Product ID should be 1");
        Assertions.assertEquals("PD789", productResponse.getProductDetails().getProductDetailsId(), "ProductDetails ID should be PD789");

        // Verify repository calls
        Mockito.verify(productRepository, times(1)).findById(1L);
        Mockito.verify(productDetailsRepository, times(1)).findProductDetailsByProductId(1L);
    }
    @Test
    public void updateProductAndProductDetailsTest() {
        Long productId = 1L;

        // Mock existing product and product details
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        Mockito.when(productDetailsRepository.findProductDetailsByProductId(productId)).thenReturn(Optional.of(mockProductDetails));

        // Mock save behavior
        Mockito.when(productRepository.save(any(Product.class))).thenReturn(mockProduct);
        Mockito.when(productDetailsRepository.save(any(ProductDetails.class))).thenReturn(mockProductDetails);

        // Execute update method
        service.updateProductById(productId, productRequest);

        Mockito.verify(productRepository, times(1)).save(any(Product.class));
        Mockito.verify(productDetailsRepository, times(1)).save(any(ProductDetails.class));
    }

    @Test
    public void deleteProductAndProductDetailsTest() {
        Long productId = 1L;
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        Mockito.when(productDetailsRepository.findProductDetailsByProductId(productId))
                .thenReturn(Optional.of(mockProductDetails));

        service.deleteProductById(productId);

        Mockito.verify(productRepository, times(1)).findById(productId);
        Mockito.verify(productDetailsRepository, times(1)).findProductDetailsByProductId(productId);
        Mockito.verify(productRepository, times(1)).deleteById(productId); // Corrected verification
    }
}

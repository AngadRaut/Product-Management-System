package com.pms.controllers;

import com.pms.controller.ProductRequestController;
import com.pms.dto.ProductRequest;
import com.pms.dto.ProductResponse;
import com.pms.entities.Product;
import com.pms.models.ProductDetails;
import com.pms.services.ProductAndProductDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductRequestControllerTest {
    @Mock
    private ProductAndProductDetailsService service;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ProductRequestController controller;

    private ProductRequest productRequest;
    private Product mockProduct;
    private ProductDetails mockProductDetails;
    private ProductResponse mockProductResponse;
    private List<ProductResponse> mockProductList;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

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
        mockProductResponse = new ProductResponse();
        mockProductResponse.setProduct(mockProduct);
        mockProductResponse.setProductDetails(mockProductDetails);
        mockProductList = List.of(mockProductResponse);

        when(service.saveProductAndProductDetails(any(ProductRequest.class))).thenReturn("Product Saved Successfully");
        mockProductResponse = new ProductResponse();

        when(service.findProductById(1L)).thenReturn(mockProductResponse);
        when(service.findProductByProductName("Washing Machine")).thenReturn(mockProductResponse);
        when(service.findAllProduct()).thenReturn(mockProductList);
    }
    @Test
    public void testAddProductSuccess() {
        when(bindingResult.hasErrors()).thenReturn(false);
        ResponseEntity<?> response = controller.add(productRequest, bindingResult);
//        assertEquals(HttpStatus.CREATED, response.getStatusCodeValue());
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Product Saved Successfully", response.getBody());
    }
    @Test
    public void testFindProductById() {
        ResponseEntity<?> response = controller.getProductById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProductResponse, response.getBody());
    }

    @Test
    public void testFindProductByProductName() {
        ResponseEntity<?> response = controller.getProductByProductName("Washing Machine");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockProductResponse, response.getBody());
    }
    @Test
    public void testGetAllProducts() {
        when(service.findAllProduct()).thenReturn(mockProductList);
        ResponseEntity<?> response = controller.getAllProduct();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockProductList, response.getBody());

        verify(service, times(1)).findAllProduct();
    }
    @Test
    public void testDeleteProductById() {
        doNothing().when(service).deleteProductById(1L);

        ResponseEntity<?> response = controller.deleteProductById(1L);

        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("Product deleted successfully", response.getBody());  // wrong
        Map<String, Object> expectedResponse = Map.of("productId", 1L, "message", "Product deleted successfully");
        assertEquals(expectedResponse, response.getBody());

    }
}

package com.pms.controllers;

import com.pms.controller.SellerController;
import com.pms.entities.Category;
import com.pms.entities.Seller;
import com.pms.services.impl.SellerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@SpringBootTest
public class SellerControllerTest {
    @Mock
    private SellerServiceImpl service;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private SellerController sellerController;

    Optional<Seller> seller =Optional.of(new Seller(90L,"bhanu","7218997215","bhanu@gmail.com","TDIT",true,"address12345","address9089",null));

    private List<Seller> sellerList ;
    private List<ObjectError> errors = new ArrayList<>();
    ObjectError error = new ObjectError("error","validations must be satisfied!");

    @BeforeEach
    public void setUp(){
        sellerList = Arrays.asList( new Seller(
                90L,
                "bhanu",
                "7218997215",
                "bhanu@gmail.com",
                "TDIT",
                true,
                "address12345",
                "address9089",
                null
        ),new Seller(
                99L,
                "Alice Smith",
                "9123456789",
                "alice.smith@retailhub.com",
                "Retail Hub Pvt. Ltd.",
                false,  // Not verified yet
                "456 Market Road, Los Angeles",
                "Building 5A",
                null
        ));
        when(service.saveSeller(seller.get())).thenReturn(seller.get());
        when(service.findSellerBySellerName("Electrical1")).thenReturn(seller.get());
        when(this.service.findAllSeller()).thenReturn(sellerList);
        when(service.findSellerById(90L)).thenReturn(seller.get());
        errors.add(error);

        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(errors);
    }
    @Test
    void testDeleteSellerById_Success() {
        Long sellerId = 1L;
        Mockito.doNothing().when(service).deleteSellerById(sellerId);

        ResponseEntity<?> response = sellerController.deleteSellerById(sellerId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(Map.class, response.getBody());

        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Seller deleted successfully", responseBody.get("message"));
        assertEquals(sellerId, responseBody.get("sellerId"));

        Mockito.verify(service, times(1)).deleteSellerById(sellerId);
    }
    @Test
    public void addSellerTest(){
        Seller seller1 = service.saveSeller(seller.get());
        Assertions.assertNotNull(seller1);
        Assertions.assertEquals(seller.get().getSellerId(),seller1.getSellerId());
        Assertions.assertEquals(seller.get().getEmail(),seller1.getEmail());
        Assertions.assertEquals(seller.get().getAddress1(),seller1.getAddress1());
    }
    @Test
    public void addSeller_NegativeTest(){
        ResponseEntity<String> response = sellerController.addSeller(seller.get(), bindingResult);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Invalid input."));
    }

    @Test
    public void addSeller_ValidationFailureTest() {
        Seller invalidCategory = new Seller();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(List.of(new ObjectError("seller", "Invalid input")));

        ResponseEntity<String> response = sellerController.addSeller(invalidCategory, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Invalid input."));
    }

    @Test
    void testGetAllSellers_ReturnsSellers() {
        Mockito.when(service.findAllSeller()).thenReturn(sellerList);

        ResponseEntity<?> response = sellerController.getAllSellers();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);

        List<Seller> actualCategories = (List<Seller>) response.getBody();
        assertEquals(2, actualCategories.size());
        assertEquals("bhanu", actualCategories.get(0).getSellerName());
        assertEquals("Alice Smith", actualCategories.get(1).getSellerName());

        Mockito.verify(service, times(1)).findAllSeller();
    }
    @Test
    void testGetAllCategories_ReturnsNotFound_WhenNoCategoriesExist() {
        Mockito.when(service.findAllSeller()).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = sellerController.getAllSellers();

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, String> responseMap = (Map<String, String>) response.getBody();
        assertEquals("No sellers found.", responseMap.get("message"));
        Mockito.verify(service, times(1)).findAllSeller();
    }
}
package com.pms.controllers;

import com.pms.controller.SellerController;
import com.pms.entities.Seller;
import com.pms.services.impl.SellerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


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
                null // Assuming no products are assigned initially
        ),new Seller(
                99L,
                "Alice Smith",
                "9123456789",
                "alice.smith@retailhub.com",
                "Retail Hub Pvt. Ltd.",
                false,  // Not verified yet
                "456 Market Road, Los Angeles",
                "Building 5A",
                null // No products assigned initially
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
    public void addSellerTest(){
        Seller seller1 = service.saveSeller(seller.get());
        Assertions.assertNotNull(seller1);
        Assertions.assertEquals(seller.get().getSellerId(),seller1.getSellerId());
        Assertions.assertEquals(seller.get().getEmail(),seller1.getEmail());
        Assertions.assertEquals(seller.get().getAddress1(),seller1.getAddress1());
    }
    @Test
    public void addCategory_NegativeTest(){
        ResponseEntity<String> response = sellerController.addSeller(seller.get(), bindingResult);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Invalid input."));
    }


}

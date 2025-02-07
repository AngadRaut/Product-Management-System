package com.pms.services;

import com.pms.custom_exceptions.ResourceNotFoundException;
import com.pms.entities.Seller;
import com.pms.repository.SellerRepository;
import com.pms.services.impl.SellerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@SpringBootTest
public class SellerServiceTest {
    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private SellerServiceImpl sellerService;

    private  Seller existingSeller;
    private Seller  updatedSeller;

    Optional<Seller> sellerOptional = Optional.of( new Seller(
            1L,
            "John Doe",
            "9876543210",
            "johndoe@example.com",
            "Tech Solutions Ltd.",
            true,
            "123 Main Street, New York",
            "Suite 400",
            null // Assuming no products are assigned initially
    ));

    List<Seller> sellerList;

    @BeforeEach
    public void setUp(){
        // add 2 records in list
        sellerList = Arrays.asList( new Seller(
                1L,
                "John Doe",
                "9876543210",
                "johndoe@example.com",
                "Tech Solutions Ltd.",
                true,
                "123 Main Street, New York",
                "Suite 400",
                null // Assuming no products are assigned initially
        ),new Seller(
                2L,
                "Alice Smith",
                "9123456789",
                "alice.smith@retailhub.com",
                "Retail Hub Pvt. Ltd.",
                false,  // Not verified yet
                "456 Market Road, Los Angeles",
                "Building 5A",
                null // No products assigned initially
        ));

        Mockito.when(this.sellerRepository.save(sellerOptional.get())).thenReturn(sellerOptional.get());
        Mockito.when(this.sellerRepository.findById(1L)).thenReturn(sellerOptional);
        Mockito.when(this.sellerRepository.findAll()).thenReturn(sellerList);
        Mockito.when(this.sellerRepository.findSellerBySellerName("John Doe")).thenReturn(sellerOptional);



         existingSeller = new Seller(
                9L, "John Doe", "9876543210", "johndoe@example.com",
                "Tech Solutions Ltd.", true, "123 Main Street, New York",
                "Suite 400", null
        );

         updatedSeller = new Seller(
                6L, "John Smith", "9123456789", "johnsmith@example.com",
                "Tech Innovations Ltd.", true, "789 Market Street, Chicago",
                "Building 10", null
        );
        Mockito.when(sellerRepository.findById(9L)).thenReturn(Optional.of(existingSeller));
        Mockito.when(sellerRepository.save(any(Seller.class))).thenReturn(updatedSeller);

        sellerService.updateSeller(9L, updatedSeller);
    }

    @Test
    public void  saveSellerTest(){
        Seller seller = this.sellerService.saveSeller(sellerOptional.get());
        Assertions.assertNotNull(seller);
        Assertions.assertEquals(updatedSeller,seller);
        Assertions.assertEquals(updatedSeller.getSellerId(),seller.getSellerId());
    }

    @Test
    public void  findSellerBySellerNameTest(){
        Seller johnDoe = this.sellerService.findSellerBySellerName("John Doe");
        Assertions.assertEquals(sellerOptional.get(),johnDoe);
    }
    @Test
    public void findSellerBySellerName_negativeTest(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            this.sellerService.findSellerBySellerName("J");
        });
    }


    @Test
    public void  findSellerByIdTest(){
        Seller sellerById = this.sellerService.findSellerById(1L);
        Assertions.assertEquals(sellerOptional.get(),sellerById);
        Assertions.assertEquals(sellerOptional.get().getSellerId(),sellerById.getSellerId());
    }
    @Test
    public void findSellerById_negativeTest(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            this.sellerService.findSellerById(5L);
        });
    }

    @Test
    public void  deleteSellerByIdTest(){
        Long sellerId = 1L;
        Mockito.when(sellerRepository.existsById(sellerId)).thenReturn(true);
        // Act
        sellerService.deleteSellerById(sellerId);

        // Assert
        Mockito.verify(sellerRepository, times(1)).existsById(sellerId);
        Mockito.verify(sellerRepository, times(1)).deleteById(sellerId);
    }

    @Test
    public void  findAllSellerTest(){
        List<Seller> all = this.sellerRepository.findAll();
        Assertions.assertEquals(sellerList,all);
        Assertions.assertEquals(sellerList.size(),all.size());
    }

    @Test
    public void updateSellerTest(){
        Mockito.verify(sellerRepository, times(1)).findById(9L);
        Mockito.verify(sellerRepository, times(1)).save(any(Seller.class));

        Assertions.assertEquals("John Smith", existingSeller.getSellerName());
        Assertions.assertEquals("9123456789", existingSeller.getMobileNo());
        Assertions.assertEquals("johnsmith@example.com", existingSeller.getEmail());
        Assertions.assertEquals("Tech Innovations Ltd.", existingSeller.getCompanyName());
        Assertions.assertEquals("789 Market Street, Chicago", existingSeller.getAddress1());
    }

  /*  @Test
    public void updateSeller_NegativeTest() {
        // Arrange
        Long sellerId = 99L;
        Seller updatedSeller = new Seller(
                sellerId, "John Smith", "9123456789", "johnsmith@example.com",
                "Tech Innovations Ltd.", true, "789 Market Street, Chicago",
                "Building 10", null
        );
        Mockito.when(sellerRepository.findById(sellerId)).thenReturn(Optional.empty());
        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            sellerService.updateSeller(sellerId, updatedSeller);
        });
        Assertions.assertTrue(thrown.getMessage().contains("Seller with id " + sellerId + " not found"));

        // Verify findById was called once
        Mockito.verify(sellerRepository, times(1)).findById(sellerId);
        Mockito.verify(sellerRepository, times(0)).save(any(Seller.class));
    }*/
}

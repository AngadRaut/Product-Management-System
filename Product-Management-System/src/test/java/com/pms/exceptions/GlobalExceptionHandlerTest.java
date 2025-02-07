package com.pms.exceptions;


import com.pms.custom_exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GlobalExceptionHandlerTest {
    @Test
    public void GlobalExceptionHandlerTest(){
        Long id = 12L;
        String msg = "not found with id {id}";
        String expectedMsg = "not found with id 12";


    }
}

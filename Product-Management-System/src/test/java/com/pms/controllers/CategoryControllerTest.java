package com.pms.controllers;

import com.pms.controller.CategoryController;
import com.pms.entities.Category;
import com.pms.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoryControllerTest {
    @Mock
    private CategoryServiceImpl categoryService;

    @Mock
    private BindingResult bindingResult;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private CategoryController categoryController;

    Optional<Category> category = Optional.of(new Category(56L,"Electrical1"
            ,"best for home1",null));

    private List<Category> categoryList ;
    private List<ObjectError> errors = new ArrayList<>();
    ObjectError error = new ObjectError("error","validations must be satisfied!");

    @BeforeEach
    public void setUp(){
        categoryList = Arrays.asList(new Category(56L, "Sample Product", "Sample product description", null)
                ,new Category(2L, "Sample Product222", "Sample product description222", null));

        when(categoryService.findCategoryById(category.get().getCategoryId())).thenReturn(category.get());
        when(categoryService.saveCategory(category.get())).thenReturn(category.get());
        when(categoryService.findCategoryByName("Electrical1")).thenReturn(category.get());
        when(this.categoryService.findAllCategory()).thenReturn(categoryList);

        errors.add(error);

        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(errors);

    }
    @Test
    public void addCategoryTest(){
        Category category1 = categoryService.saveCategory(category.get());
        Assertions.assertNotNull(category1);
        assertEquals(category.get(),category1);
        assertEquals(category.get().getName(),category1.getName());
        assertEquals(category.get().getCategoryId(),category1.getCategoryId());
    }
    @Test
    public void addCategory_NegativeTest(){
        ResponseEntity<String> response = categoryController.addCategory(category.get(), bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Invalid input."));
    }

    @Test
    public void getCategoryByIdTest(){
        Category categoryById = categoryService.findCategoryById(56L);

        

    }

}

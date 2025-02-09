package com.pms.controllers;

import com.pms.controller.CategoryController;
import com.pms.custom_exceptions.ResourceNotFoundException;
import com.pms.entities.Category;
import com.pms.services.impl.CategoryServiceImpl;
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
import static org.mockito.Mockito.*;

@SpringBootTest
public class CategoryControllerTest {
    @Mock
    private CategoryServiceImpl categoryService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private CategoryController categoryController;

    Optional<Category> category = Optional.of(new Category(56L, "Electrical1"
            , "best for home1", null));

    private List<Category> categoryList;
    private List<ObjectError> errors = new ArrayList<>();
    ObjectError error = new ObjectError("error", "validations must be satisfied!");

    @BeforeEach
    public void setUp() {
        categoryList = Arrays.asList(new Category(56L, "Sample Product", "Sample product description", null)
                , new Category(2L, "Sample Product222", "Sample product description222", null));

        when(categoryService.findCategoryById(category.get().getCategoryId())).thenReturn(category.get());
        when(categoryService.saveCategory(category.get())).thenReturn(category.get());
        when(categoryService.findCategoryByName("Electrical1")).thenReturn(category.get());
        when(this.categoryService.findAllCategory()).thenReturn(categoryList);

        errors.add(error);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(errors);
    }

    @Test
    public void addCategoryTest() {
        Category category1 = categoryService.saveCategory(category.get());
        Assertions.assertNotNull(category1);
        assertEquals(category.get(), category1);
        assertEquals(category.get().getName(), category1.getName());
        assertEquals(category.get().getCategoryId(), category1.getCategoryId());
    }

    @Test
    public void addCategory_NegativeTest() {
        ResponseEntity<String> response = categoryController.addCategory(category.get(), bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Invalid input."));
    }

    @Test
    void addCategory() {
        when(categoryController.addCategory(category.get(),bindingResult)).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
        ResponseEntity<String> response = categoryController.addCategory(category.get(), bindingResult);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void addCategory_ValidationFailureTest() {
        Category invalidCategory = new Category();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(List.of(new ObjectError("category", "Invalid input")));

        ResponseEntity<String> response = categoryController.addCategory(invalidCategory, bindingResult);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Invalid input."));
    }

    @Test
    public void getCategoryByIdTest() {
        Category categoryById = categoryService.findCategoryById(56L);
        assertNotNull(categoryById);
        assertEquals(category.get(), categoryById);
        assertEquals(category.get().getCategoryId(), categoryById.getCategoryId());
        assertEquals(category.get().getDescription(), categoryById.getDescription());
        assertEquals(category.get().getName(), categoryById.getName());
    }

  /*  @Test
    public void getCategoryById_NegativeTest() {
        Long categoryId = 54L;
        Mockito.when(categoryService.findCategoryById(categoryId))
                .thenThrow(new ResourceNotFoundException("Category with ID " + categoryId + " not found"));

        ResponseEntity<?> response = categoryController.getCategoryById(categoryId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("error"));
        assertTrue(response.getBody().toString().contains("Category with ID " + categoryId + " not found"));
    }*/

    @Test
    void testGetAllCategories_ReturnsCategories() {
        Mockito.when(categoryService.findAllCategory()).thenReturn(categoryList);

        ResponseEntity<?> response = categoryController.getAllCategories();
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);

        List<Category> actualCategories = (List<Category>) response.getBody();
        assertEquals(2, actualCategories.size());
        assertEquals("Sample Product", actualCategories.get(0).getName());
        assertEquals("Sample Product222", actualCategories.get(1).getName());

        Mockito.verify(categoryService, times(1)).findAllCategory();
    }
/*
    @Test
    void testGetAllCategories_ReturnsNotFound_WhenNoCategoriesExist() {
        Mockito.when(categoryService.findAllCategory()).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = categoryController.getAllCategories();

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertInstanceOf(Map.class, response.getBody());
        Map<String, String> responseMap = (Map<String, String>) response.getBody();
        assertEquals("No Category found.", responseMap.get("message"));
        Mockito.verify(categoryService, times(1)).findAllCategory();
    }*/


    @Test
    void testDeleteCategoryById_Success() {
        // Arrange
        Long categoryId = 1L;
        Mockito.doNothing().when(categoryService).deleteCategoryById(categoryId);

        // Act
        ResponseEntity<?> response = categoryController.deleteCategoryById(categoryId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(Map.class, response.getBody());

        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Category deleted successfully", responseBody.get("message"));
        assertEquals(categoryId, responseBody.get("categoryId"));

        // Verify
        Mockito.verify(categoryService, times(1)).deleteCategoryById(categoryId);
    }
}
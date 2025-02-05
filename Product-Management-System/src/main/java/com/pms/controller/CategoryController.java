package com.pms.controller;

import com.pms.entities.Category;
import com.pms.services.CategoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    // add category
    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@Valid @RequestBody Category category, BindingResult bindingResult){
        log.info("Received request to add category.");
        // Validate the input
        if (bindingResult.hasErrors()) {
            log.warn("Validation failed for category request: {}", bindingResult.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        Category category1 = this.categoryService.saveCategory(category);
        log.info("Category successfully added in records categoryId:{}",category1.getCategoryId());
        return ResponseEntity.status(HttpStatus.CREATED).body("Category added categoryId:"+category1.getCategoryId());
    }

    // find product by id
    @GetMapping("/get/byId/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long categoryId) {
        log.info("Received request to get category by ID: {}", categoryId);
        Category category = categoryService.findCategoryById(categoryId);
        log.info("Category with ID: {} found successfully!", categoryId);
        return ResponseEntity.ok(category);
    }
    @GetMapping("/get/all")
    public ResponseEntity<String> getAllCategories() {
        log.info("Received request to fetch all categories.");
        List<Category> categories = categoryService.findAllCategory();

        if (categories.isEmpty()) {
            log.warn("No categories found in the database.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No categories found in the database.");
        }

        log.info("Categories retrieved successfully. Total categories: {}", categories.size());
        return ResponseEntity.status(HttpStatus.OK).body("Categories retrieved successfully. Total categories:"+categories.size());
    }

    // find category by name
    @GetMapping("/get/name/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable("name")String categoryName){
        log.info("Received request to fetch category with name: {}", categoryName);
        Category categoryByName = categoryService.findCategoryByName(categoryName);
        log.info("Successfully retrieved category: {}", categoryByName);
        return ResponseEntity.ok(categoryByName);
    }

    // delete category
    @DeleteMapping("/delete/byId/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable("id") Long categoryId) {
        log.info("Received request to delete category with ID: {}", categoryId);
        categoryService.deleteCategoryById(categoryId);
        log.info("Category with ID: {} deleted successfully.", categoryId);
        return ResponseEntity.status(HttpStatus.OK).body("Category with ID " + categoryId + " deleted successfully.");
    }
}

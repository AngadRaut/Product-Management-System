package com.pms.controller;

import com.pms.entities.Category;
import com.pms.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // add category
    @PostMapping("/add")
    public ResponseEntity<String> addCategory(@Valid @RequestBody Category category){
        Category category1 = this.categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category stored successfully!!"+category1);
    }
}

package com.pms.services.impl;

import com.pms.custom_exceptions.ResourceNotFoundException;
import com.pms.entities.Category;
import com.pms.custom_exceptions.AlreadyExistsException;
import com.pms.repository.CategoryRepository;
import com.pms.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        log.info("Received request to save category : {}", category);
        return this.categoryRepository.save(category);
    }

    @Override
    public Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.warn("Category for ID: {} is not present in records.", categoryId);
                    return new ResourceNotFoundException("Category with id " + categoryId + " is not present in the records.");
                });
    }

    @Override
    public List<Category> findAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        log.info("Fetched {} categories from the database.", categories.size());
        return categories;
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.warn("Category with ID {} not found.", categoryId);
                    return new ResourceNotFoundException("Category with ID " + categoryId + " is not present in the record.");
                });

        categoryRepository.delete(category);
        log.info("Category with ID {} deleted successfully.", categoryId);
    }

    @Override
    public Category findCategoryByName(String categoryName) {
        log.info("Fetching category by name: {}", categoryName);
        Category category = categoryRepository.findCategoryByName(categoryName)
                .orElseThrow(() -> {
                    log.warn("Category with name {} not found.", categoryName);
                    return new ResourceNotFoundException("Category with name " + categoryName + " is not present in the record.");
                });
        return category;
    }
}
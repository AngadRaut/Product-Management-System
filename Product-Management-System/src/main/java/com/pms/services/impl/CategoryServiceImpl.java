package com.pms.services.impl;

import com.pms.entities.Category;
import com.pms.custom_exceptions.AlreadyExistsException;
import com.pms.repository.CategoryRepository;
import com.pms.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
            return this.categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findCategoryById(Long categoryId) {
        return Optional.empty();
    }

    @Override
    public Category findCategoryByBankName(String categoryName) {
        return null;
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        return null;
    }

    @Override
    public List<Category> findAllCategory() {
        return List.of();
    }

    @Override
    public void deleteCategoryById(Long categoryId) {

    }
}

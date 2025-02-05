package com.pms.services;

import com.pms.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public Category saveCategory(Category category);
    public Category findCategoryById(Long categoryId);
    public List<Category> findAllCategory();
    public void deleteCategoryById(Long categoryId);
}

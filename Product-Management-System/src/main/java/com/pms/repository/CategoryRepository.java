package com.pms.repository;

import com.pms.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    public Optional<Category> findCategoryByName(String categoryName);
}

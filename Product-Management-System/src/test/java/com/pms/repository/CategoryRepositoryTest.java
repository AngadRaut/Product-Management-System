package com.pms.repository;

import com.pms.custom_exceptions.ResourceNotFoundException;
import com.pms.entities.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    Category category = new Category(null,"Electrical1","best for home1",null);

    @BeforeEach
    public void setUp(){
        categoryRepository.save(category);
//        testEntityManager.persistAndFlush(category);
    }

    @Test
    @Commit
    public void findCategoryBYCategoryNameTest(){
        Category category1 = categoryRepository.findCategoryByName("Electrical1").get();
        Assertions.assertEquals(category.getCategoryId(),category1.getCategoryId());
        Assertions.assertEquals(category.getName(),category1.getName());
        Assertions.assertEquals(category.getDescription(),category1.getDescription());

    }

    @Test
    public void findCategoryByNameNegativeTest(){
        Assertions.assertThrows(ResourceNotFoundException.class,()->{
            categoryRepository.findCategoryByName("Electric1").orElseThrow(()->new ResourceNotFoundException("not found"));
        });
    }
}
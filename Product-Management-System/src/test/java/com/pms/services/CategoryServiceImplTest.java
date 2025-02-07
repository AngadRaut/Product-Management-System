package com.pms.services;

import com.pms.custom_exceptions.ResourceNotFoundException;
import com.pms.entities.Category;
import com.pms.repository.CategoryRepository;
import com.pms.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category existingCategory;
    private Category  updatedCategory;

    Optional<Category> category = Optional.of(new Category(1L, "Sample Product", "Sample product description", null));
    List<Category> categoryList;
    private Long existingCategoryId = 1L;
    private Long nonExistingCategoryId = 5L;

    @BeforeEach
    public void setUp(){
        categoryList = Arrays.asList(new Category(1L, "Sample Product", "Sample product description", null)
                ,new Category(2L, "Sample Product222", "Sample product description222", null));

        Mockito.when(this.categoryRepository.save(category.get())).thenReturn(category.get());
        Mockito.when(this.categoryRepository.findById(1L)).thenReturn(category);
        Mockito.when(this.categoryRepository.findAll()).thenReturn(categoryList);
        Mockito.when(this.categoryRepository.findCategoryByName("Sample Product")).thenReturn(category);

        existingCategory = new  Category(
                78L,
                "Home Appliances",
                "Category for household appliances",
                null // Assuming no associated products initially
        );

        updatedCategory = new Category(
                79L,
                "Electronics",
                "Category for electronic devices",
                null // Assuming no associated products initially
        );
        Mockito.when(categoryRepository.findById(78L)).thenReturn(Optional.of(existingCategory));
        Mockito.when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        categoryService.updateCategory(78L, updatedCategory);
    }

    @Test
    public void saveCategoryTest(){
       /* Category category1 = this.categoryService.saveCategory(category.get());
        Assertions.assertEquals(category.get(),category1);*/

        Category savedCategory = categoryService.saveCategory(category.get());

        Assertions.assertEquals(updatedCategory.getName(), savedCategory.getName());
        Assertions.assertEquals(updatedCategory.getDescription(), savedCategory.getDescription());

    }

    @Test
    public void findAllCategoryTest(){
        List<Category> allCategory = this.categoryService.findAllCategory();
        Assertions.assertEquals(categoryList,allCategory);
        Assertions.assertEquals(categoryList.size(),allCategory.size());
    }

    @Test
    public void findCategoryByIdTest(){
        Category categoryById = this.categoryService.findCategoryById(1L);
        Assertions.assertEquals(category.get(),categoryById);
        Assertions.assertEquals(category.get().getName(),categoryById.getName());
        Assertions.assertEquals(category.get().getDescription(),categoryById.getDescription());
    }

    @Test
    public void saveCategoryTest_NotFound(){
        Assertions.assertThrows(ResourceNotFoundException.class
                ,()->categoryService.findCategoryById(4L));
    }

    @Test
    public void findCategoryByNameTest(){
        Category categoryByName = this.categoryService.findCategoryByName("Sample Product");
        Assertions.assertNotNull(categoryByName);
        Assertions.assertEquals(category.get(),categoryByName);
        Assertions.assertEquals(category.get().getCategoryId(),categoryByName.getCategoryId());
        Assertions.assertEquals(category.get().getDescription(),categoryByName.getDescription());
    }

    @Test
    public void findCategoryByNameTest_negativeTest(){
        Assertions.assertThrows(ResourceNotFoundException.class,()->categoryService.findCategoryByName("nana"));
    }

    @Test
    public void deleteCategoryByIdTest(){
        categoryService.deleteCategoryById(1L);
        Mockito.verify(categoryRepository,Mockito.times(2)).deleteById(1L);
      /*  Category categoryById1 = this.categoryService.findCategoryById(1L);

        Assertions.assertEquals(null,categoryById1);*/
//        Assertions.assertNull(categoryById1);
//        Assertions.assertThrows(ResourceNotFoundException.class,()->categoryService.findCategoryById(1L));
    }

    @Test
    public void updateCategoryTest(){
        Mockito.verify(categoryRepository,Mockito.times(1)).findById(78L);
        Mockito.verify(categoryRepository,Mockito.times(1)).save(any(Category.class));

        Assertions.assertEquals("Electronics",existingCategory.getName());
        Assertions.assertEquals("Category for electronic devices",existingCategory.getDescription());
    }
}

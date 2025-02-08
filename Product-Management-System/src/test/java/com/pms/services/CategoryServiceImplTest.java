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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

        when(this.categoryRepository.save(category.get())).thenReturn(category.get());
        when(this.categoryRepository.findById(1L)).thenReturn(category);
        when(this.categoryRepository.findAll()).thenReturn(categoryList);
        when(this.categoryRepository.findCategoryByName("Sample Product")).thenReturn(category);

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
        when(categoryRepository.findById(78L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);
        categoryService.updateCategory(78L, updatedCategory);
    }
    @Test
    public void saveCategoryTest(){
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
  public void deleteCategoryByIdTest() {
      Long categoryId = 1L;
      Category category = new Category(categoryId, "Sample Product", "Sample product description", null);

      when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
      doNothing().when(categoryRepository).delete(category);

      categoryService.deleteCategoryById(categoryId);
      verify(categoryRepository, times(1)).findById(categoryId);
      verify(categoryRepository, times(1)).delete(category);
  }

    @Test
    public void updateCategoryTest(){
        verify(categoryRepository, times(1)).findById(78L);
        verify(categoryRepository, times(1)).save(any(Category.class));

        Assertions.assertEquals("Electronics",existingCategory.getName());
        Assertions.assertEquals("Category for electronic devices",existingCategory.getDescription());
    }
}

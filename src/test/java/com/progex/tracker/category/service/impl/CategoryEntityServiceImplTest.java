package com.progex.tracker.category.service.impl;

import com.progex.tracker.category.entity.CategoryEntity;
import com.progex.tracker.category.repo.CategoryRepository;
import com.progex.tracker.category.service.CategoryService;
import com.progex.tracker.exceptions.EntityNotFoundException;
import com.progex.tracker.item.repo.ItemRepository;
import com.progex.tracker.item.service.ItemService;
import com.progex.tracker.item.service.impl.ItemServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CategoryEntityServiceImplTest {

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;


    @TestConfiguration
    static class CategoryServiceImplTestContextConfiguration {

        @Bean
        public CategoryService categoryService() {

            return new CategoryServiceImpl();
        }

        @Bean
        public ItemService itemService() {
            return new ItemServiceImpl();
        }
    }

    @Test
    public void shouldReturnCategoryWhenSaving() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1);
        categoryEntity.setName("category1");

        when(categoryRepository.save(categoryEntity)).
                thenReturn(categoryEntity);

        CategoryEntity returnedCategoryEntity = categoryService.createCategory(categoryEntity);
        assertNotNull(returnedCategoryEntity);
        assertEquals(categoryEntity, returnedCategoryEntity);
    }

    @Test
    public void shouldReturnCategoryWhenCallingGetByIdWithAValidCategoryId() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1);
        categoryEntity.setName("categoryEntity");

        when(categoryRepository.findById(categoryEntity.getId())).
                thenReturn(Optional.of(categoryEntity));

        Optional<CategoryEntity> returnedCategory = categoryService.getCategoryById(categoryEntity.getId());

        assertTrue(returnedCategory.isPresent());
        assertEquals(categoryEntity, returnedCategory.get());
    }

    @Test
    public void shouldDeleteCategoryWhenWithAValidId(){
        categoryService.deleteById(1);
        verify(categoryRepository, times(1)).deleteById(anyInt());
    }
}
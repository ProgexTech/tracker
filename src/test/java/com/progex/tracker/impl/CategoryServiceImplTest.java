package com.progex.tracker.impl;

import com.progex.tracker.entity.Category;
import com.progex.tracker.entity.Item;
import com.progex.tracker.repo.CategoryRepository;
import com.progex.tracker.repo.ItemRepository;
import com.progex.tracker.service.CategoryService;
import com.progex.tracker.service.ItemService;
import com.progex.tracker.exceptions.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CategoryServiceImplTest {

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
        Category category = new Category();
        category.setId(1);
        category.setName("category1");

        when(categoryRepository.save(category)).
                thenReturn(category);

        Category returnedCategory = categoryService.save(category);
        assertNotNull(returnedCategory);
        assertEquals(category, returnedCategory);
    }

    @Test
    public void shouldSaveItemWhenAddingItem() {
        Item item = new Item();
        item.setName("Rice");
        item.setDescription("Sri Lankan Rice");

        Category category = new Category();
        category.setId(1);
        category.setName("category");

        when(itemRepository.save(item)).thenReturn(item);
        when(categoryRepository.findById(category.getId())).
                thenReturn(Optional.of(category));

        categoryService.addItem(category.getId(), item);

        verify(categoryRepository, times(1)).save(any());
    }

    @Test
    public void shouldReturnCategoryWhenCallingGetByIdWithAValidCategoryId() {
        Category category = new Category();
        category.setId(1);
        category.setName("category");

        when(categoryRepository.findById(category.getId())).
                thenReturn(Optional.of(category));

        Category returnedCategory = categoryService.findById(category.getId());

        assertNotNull(returnedCategory);
        assertEquals(category, returnedCategory);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundWhenCallingGetByIdWithInValidCategoryId() {
        categoryService.findById(1);
    }
}

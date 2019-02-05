package com.progex.tracker.item.service.impl;

import com.progex.tracker.item.entity.Item;
import com.progex.tracker.item.repo.ItemRepository;
import com.progex.tracker.item.service.ItemService;
import com.progex.tracker.category.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.progex.tracker.utility.TestUtils.getMockItem;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ItemServiceImplTest {
    @MockBean
    private ItemRepository repository;

    @MockBean
    CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @TestConfiguration
    static class ItemServiceImplTestContextConfiguration {

        @Bean
        public ItemService itemService() {
            return new ItemServiceImpl();
        }
    }

    @Test
    public void shouldCreateItemWhenInvokingCreate() {
        Item item = getMockItem();

        when(repository.save(item)).thenReturn(item);

        Optional<Item> savedItem = itemService.insert(item);
        assertTrue(savedItem.isPresent());
        assertEquals(item, savedItem.get());
    }

    @Test
    public void shouldReturnItemWhenInvokingGetByIdWithValidId() {
        Item item = getMockItem();

        when(repository.findById(item.getId())).thenReturn(Optional.of(item));

        Optional<Item> returnedItem = itemService.getItemById(item.getId());
        assertTrue(returnedItem.isPresent());
        assertEquals(item, returnedItem.get());
    }

    @Test
    public void shouldThrowEntityNotFoundWhenInvokingGetByIdWithInvalidId() {
        assertTrue(itemService.getItemById(1).isEmpty());
    }
}
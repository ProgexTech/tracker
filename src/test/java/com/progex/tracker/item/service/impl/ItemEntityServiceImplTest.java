package com.progex.tracker.item.service.impl;

import com.progex.tracker.category.service.CategoryService;
import com.progex.tracker.item.entity.ItemEntity;
import com.progex.tracker.item.repo.ItemRepository;
import com.progex.tracker.item.service.ItemService;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ItemEntityServiceImplTest {
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
        ItemEntity itemEntity = getMockItem();

        when(repository.save(itemEntity)).thenReturn(itemEntity);

        ItemEntity savedItem = itemService.insert(itemEntity);
        assertNotNull(savedItem);
        assertEquals(itemEntity, savedItem);
    }

    @Test
    public void shouldReturnItemWhenInvokingGetByIdWithValidId() {
        ItemEntity itemEntity = getMockItem();

        when(repository.findById(itemEntity.getId())).thenReturn(Optional.of(itemEntity));

        Optional<ItemEntity> returnedItem = itemService.getItemById(itemEntity.getId());
        assertTrue(returnedItem.isPresent());
        assertEquals(itemEntity, returnedItem.get());
    }

    @Test
    public void shouldThrowEntityNotFoundWhenInvokingGetByIdWithInvalidId() {
        assertTrue(itemService.getItemById(1).isEmpty());
    }

    @Test
    public void shouldDeleteItemWhenCallingWithAValidId(){
        itemService.deleteById(1);
        verify(repository, times(1)).deleteById(anyInt());
    }
}
package com.progex.tracker.impl;

import com.progex.tracker.entity.Item;
import com.progex.tracker.repo.ItemRepository;
import com.progex.tracker.service.ItemService;
import com.progex.tracker.uttility.EntityNotFound;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ItemServiceImplTest {
    @MockBean
    private ItemRepository repository;

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
        Item item = new Item();
        item.setId(1);
        item.setName("Rice");
        item.setCalorie("100kj");

        when(repository.save(item)).thenReturn(item);

        Item savedItem = itemService.insert(item);
        assertNotNull(savedItem);
        assertEquals(item, savedItem);
    }

    @Test
    public void shouldReturnItemWhenInvokingGetByIdWithValidId() {
        Item item = new Item();
        item.setId(1);
        item.setName("Rice");
        item.setCalorie("100kj");

        when(repository.findById(item.getId())).thenReturn(Optional.of(item));

        Optional<Item> returnedItem = itemService.getItemById(item.getId());
        assertTrue(returnedItem.isPresent());
        assertEquals(item, returnedItem.get());
    }

    @Test(expected = EntityNotFound.class)
    public void shouldThrowEntityNotFoundWhenInvokingGetByIdWithInvalidId() {
        itemService.getItemById(1);
    }
}
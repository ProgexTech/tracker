package com.progex.tracker.impl;

import com.progex.tracker.entity.Category;
import com.progex.tracker.entity.Item;
import com.progex.tracker.repo.ItemRepository;
import com.progex.tracker.service.CategoryService;
import com.progex.tracker.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author indunil
 */

@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    CategoryService categoryService;

    @Override
    public Optional<Item> insert(Item item) {
        return Optional.of(itemRepository.save(item));
    }

    @Override
    public Optional<Item> getItemById(int id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isPresent()) {
            return itemOptional;
        }
        LOGGER.warn("Cannot find the Item with the id = {}", id);
        return Optional.empty();
    }

    @Override
    public Item update(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Optional<Category> getCategoryById(int categoryId) {
        return categoryService.findById(categoryId);
    }
}

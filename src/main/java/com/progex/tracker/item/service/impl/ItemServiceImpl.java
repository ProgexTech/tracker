package com.progex.tracker.item.service.impl;

import com.progex.tracker.category.entity.Category;
import com.progex.tracker.item.entity.Item;
import com.progex.tracker.item.repo.ItemRepository;
import com.progex.tracker.item.service.ItemService;
import com.progex.tracker.category.service.CategoryService;
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

package com.progex.tracker.item.service.impl;

import com.progex.tracker.category.entity.Category;
import com.progex.tracker.category.service.CategoryService;
import com.progex.tracker.item.entity.Item;
import com.progex.tracker.item.repo.ItemRepository;
import com.progex.tracker.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author indunil
 */

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    CategoryService categoryService;

    @Override
    public Item insert(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Optional<Item> getItemById(int id) {
        return itemRepository.findById(id);
    }

    @Override
    public Item update(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Optional<Category> getCategoryById(int categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @Override
    public void deleteById(int id) {
        itemRepository.deleteById(id);
    }
}

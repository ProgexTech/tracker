package com.progex.tracker.item.service.impl;

import com.progex.tracker.category.entity.CategoryEntity;
import com.progex.tracker.category.service.CategoryService;
import com.progex.tracker.item.entity.ItemEntity;
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
    public ItemEntity insert(ItemEntity itemEntity) {
        return itemRepository.save(itemEntity);
    }

    @Override
    public Optional<ItemEntity> getItemById(int id) {
        return itemRepository.findById(id);
    }

    @Override
    public ItemEntity update(ItemEntity itemEntity) {
        return itemRepository.save(itemEntity);
    }

    @Override
    public Optional<CategoryEntity> getCategoryById(int categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @Override
    public void deleteById(int id) {
        itemRepository.deleteById(id);
    }
}

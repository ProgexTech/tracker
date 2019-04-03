package com.progex.tracker.item.service;

import com.progex.tracker.category.entity.CategoryEntity;
import com.progex.tracker.item.entity.ItemEntity;

import java.util.Optional;

/**
 * @author indunil
 */
public interface ItemService {
    ItemEntity insert(ItemEntity itemEntity);

    Optional<ItemEntity> getItemById(int id);

    ItemEntity update(ItemEntity itemEntity);

    Optional<CategoryEntity> getCategoryById(int categoryId);

    void deleteById(int id);
}

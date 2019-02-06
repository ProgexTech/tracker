package com.progex.tracker.item.service;

import com.progex.tracker.category.entity.Category;
import com.progex.tracker.item.entity.Item;

import java.util.Optional;

/**
 * @author indunil
 */
public interface ItemService {
    Optional<Item> insert(Item item);

    Optional<Item> getItemById(int id);

    Item update(Item item);

    Optional<Category> getCategoryById(int categoryId);

    void deleteById(int id);
}

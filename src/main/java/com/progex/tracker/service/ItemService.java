package com.progex.tracker.service;

import com.progex.tracker.entity.Category;
import com.progex.tracker.entity.Item;

import java.util.Optional;

/**
 * @author indunil
 */
public interface ItemService {
    Optional<Item> insert(Item item);

    Optional<Item> getItemById(int id);

    Item update(Item item);

    Optional<Category> getCategoryById(int categoryId);
}

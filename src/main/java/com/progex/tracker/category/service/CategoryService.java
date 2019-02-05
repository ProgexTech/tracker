package com.progex.tracker.category.service;

import com.progex.tracker.category.entity.Category;
import com.progex.tracker.item.entity.Item;

import java.util.Optional;

public interface CategoryService {


    Optional<Category> save(Category category);

    void addItem(int categoryId, Item item);

    Optional<Category> findById(int categoryId);
}

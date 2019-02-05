package com.progex.tracker.service;

import com.progex.tracker.entity.Category;
import com.progex.tracker.entity.Item;

import java.util.Optional;

public interface CategoryService {


    Optional<Category> save(Category category);

    void addItem(int categoryId, Item item);

    Optional<Category> findById(int categoryId);
}

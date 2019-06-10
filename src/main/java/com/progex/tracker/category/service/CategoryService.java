package com.progex.tracker.category.service;

import com.progex.tracker.category.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {


    Category insert(Category category);

    Optional<Category> getCategoryById(int categoryId);

    List<Category> getAllCategories(int offset, int limit);

    void deleteById(int categoryId);

    Category update(int categoryId, Category category);

    boolean isExists(int categoryId);
}

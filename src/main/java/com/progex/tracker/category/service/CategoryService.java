package com.progex.tracker.category.service;

import com.progex.tracker.category.entity.Category;

import java.util.Optional;

public interface CategoryService {


    Optional<Category> createCategory(Category category);

    Optional<Category> getCategoryById(int categoryId);
}

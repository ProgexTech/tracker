package com.progex.tracker.category.service;

import com.progex.tracker.category.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {


    CategoryEntity createCategory(CategoryEntity categoryEntity);

    Optional<CategoryEntity> getCategoryById(int categoryId);

    List<CategoryEntity> getAllCategories(int offset, int limit);

    void deleteById(int categoryId);

    CategoryEntity update(int categoryId, CategoryEntity categoryEntity);

    boolean isExists(int categoryId);
}

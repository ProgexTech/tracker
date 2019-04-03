package com.progex.tracker.category.service.impl;

import com.progex.tracker.category.entity.CategoryEntity;
import com.progex.tracker.category.repo.CategoryRepository;
import com.progex.tracker.category.service.CategoryService;
import com.progex.tracker.item.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repo;

    @Autowired
    private ItemService itemService;

    @Override
    public CategoryEntity createCategory(CategoryEntity categoryEntity) {
        return repo.save(categoryEntity);
    }

    @Override
    public Optional<CategoryEntity> getCategoryById(int categoryId) {
        return repo.findById(categoryId);
    }

    @Override
    public List<CategoryEntity> getAllCategories(int offset, int limit) {
        return repo.findAllCategories(offset, limit);
    }

    @Override
    public void deleteById(int categoryId) {
        repo.deleteById(categoryId);
    }

    @Override
    public CategoryEntity update(int categoryId, CategoryEntity categoryEntity) {
        return repo.save(categoryEntity);
    }

    public boolean isExists(int categoryId) {
        return repo.existsById(categoryId);
    }
}

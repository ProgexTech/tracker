package com.progex.tracker.category.service.impl;

import com.progex.tracker.category.entity.Category;
import com.progex.tracker.category.repo.CategoryRepository;
import com.progex.tracker.category.service.CategoryService;
import com.progex.tracker.item.service.ItemService;
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
    public Category insert(Category category) {
        return repo.save(category);
    }

    @Override
    public Optional<Category> getCategoryById(int categoryId) {
        return repo.findById(categoryId);
    }

    @Override
    public List<Category> getAllCategories(int offset, int limit) {
        return repo.findAllCategories(offset, limit);
    }

    @Override
    public void deleteById(int categoryId) {
        repo.deleteById(categoryId);
    }

    @Override
    public Category update(int categoryId, Category category) {
        return repo.save(category);
    }

    public boolean isExists(int categoryId) {
        return repo.existsById(categoryId);
    }
}

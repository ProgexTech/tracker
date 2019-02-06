package com.progex.tracker.category.service.impl;

import com.progex.tracker.category.entity.Category;
import com.progex.tracker.category.repo.CategoryRepository;
import com.progex.tracker.category.service.CategoryService;
import com.progex.tracker.exceptions.EntityNotFoundException;
import com.progex.tracker.item.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository repo;

    @Autowired
    private ItemService itemService;

    @Override
    public Optional<Category> createCategory(Category category) {
        return Optional.of(repo.save(category));
    }

    @Override
    public Optional<Category> getCategoryById(int categoryId) {
        Optional<Category> categoryOptional = repo.findById(categoryId);
        if (categoryOptional.isPresent()) {
            return categoryOptional;
        } else {
            LOGGER.warn("Cannot find the Category with the id = {}", categoryId);
            throw new EntityNotFoundException("Cannot find the Category with the given id =" + categoryId);
        }
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
    public Optional<Category> update(Category category) {
        return Optional.of(repo.save(category));
    }
}

package com.progex.tracker.impl;

import com.progex.tracker.entity.Category;
import com.progex.tracker.entity.Item;
import com.progex.tracker.repo.CategoryRepository;
import com.progex.tracker.service.CategoryService;
import com.progex.tracker.service.ItemService;
import com.progex.tracker.uttility.EntityNotFound;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


public class CategoryServiceImpl implements CategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository repo;

    @Autowired
    private ItemService itemService;

    @Override
    public Category save(Category category) {
        return repo.save(category);
    }

    @Override
    public void addItem(int categoryId, Item item) {
        repo.findById(categoryId).ifPresentOrElse(category -> {
            Item savedItem = itemService.insert(item);
            category.addItem(savedItem);
            repo.save(category);
        }, () -> {
            LOGGER.error("Cannot find the category with the categoryId {}", categoryId);
            throw new EntityNotFound("Cannot find the category with the categoryId  =" + categoryId);
        });
    }

    @Override
    public Category findById(int categoryId) {
        Optional<Category> categoryOptional = repo.findById(categoryId);
        if (categoryOptional.isPresent()) {
            return categoryOptional.get();
        } else {
            LOGGER.warn("Cannot find the Category with the id = {}", categoryId);
            throw new EntityNotFound("Cannot find the Category with the given id =" + categoryId);
        }
    }
}

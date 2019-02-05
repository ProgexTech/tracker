package com.progex.tracker.category.service.impl;

import com.progex.tracker.category.entity.Category;
import com.progex.tracker.item.entity.Item;
import com.progex.tracker.category.repo.CategoryRepository;
import com.progex.tracker.category.service.CategoryService;
import com.progex.tracker.item.service.ItemService;
import com.progex.tracker.exceptions.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository repo;

    @Autowired
    private ItemService itemService;

    @Override
    public Optional<Category> save(Category category) {
        return Optional.of(repo.save(category));
    }

    @Override
    public void addItem(int categoryId, Item item) {
        repo.findById(categoryId).ifPresentOrElse(category -> {
            Optional<Item> savedItem = itemService.insert(item);
            if (savedItem.isPresent()) {
                category.addItem(savedItem.get());
                repo.save(category);
            } else {
                LOGGER.error("Cannot save given item to the database categoryId= {}", categoryId);
            }
        }, () -> {
            LOGGER.error("Cannot find the category with the categoryId {}", categoryId);
            throw new EntityNotFoundException("Cannot find the category with the categoryId =" + categoryId);
        });
    }

    @Override
    public Optional<Category> findById(int categoryId) {
        Optional<Category> categoryOptional = repo.findById(categoryId);
        if (categoryOptional.isPresent()) {
            return categoryOptional;
        } else {
            LOGGER.warn("Cannot find the Category with the id = {}", categoryId);
            throw new EntityNotFoundException("Cannot find the Category with the given id =" + categoryId);
        }
    }
}

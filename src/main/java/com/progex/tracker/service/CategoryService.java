package com.progex.tracker.service;

import com.progex.tracker.entity.Category;
import com.progex.tracker.entity.Item;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {


    Category save(Category category);

    void addItem(int categoryId, Item item);

    Category findById(int categoryId);
}

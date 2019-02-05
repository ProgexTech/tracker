package com.progex.tracker.utility;

import com.progex.tracker.category.entity.Category;
import com.progex.tracker.item.entity.Item;

public class TestUtils {

    public static Item getMockItem() {
        Category category = new Category();
        category.setId(1);
        category.setName("category");

        Item item = new Item();
        item.setId(1);
        item.setCategory(category);
        item.setName("name");
        item.setCalorie("calories");
        item.setDescription("description");
        item.setOrigin("origin");
        item.setCode("code");
        return item;
    }

}

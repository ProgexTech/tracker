package com.progex.tracker.utility;

import com.progex.tracker.entity.Item;

public class TestUtils {

    public static Item getMockItem() {
        Item item = new Item();
        item.setId(1);
        item.setName("name");
        item.setCalorie("calories");
        item.setDescription("description");
        item.setOrigin("origin");
        item.setCode("code");
        return item;
    }

}

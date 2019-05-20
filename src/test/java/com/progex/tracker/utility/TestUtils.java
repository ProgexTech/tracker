package com.progex.tracker.utility;

import com.progex.tracker.category.entity.CategoryEntity;
import com.progex.tracker.customer.entity.CustomerEntity;
import com.progex.tracker.item.entity.ItemEntity;

import java.util.Arrays;

public class TestUtils {

    public static ItemEntity getMockItem() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1);
        categoryEntity.setName("categoryEntity");

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(1);
        itemEntity.setCategoryEntity(categoryEntity);
        itemEntity.setName("name");
        itemEntity.setCalorie("calories");
        itemEntity.setDescription("description");
        itemEntity.setOrigin("origin");
        itemEntity.setCode("code");
        return itemEntity;
    }

    public static CategoryEntity getMockCategory() {
        ItemEntity itemEntity = getMockItem();
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1);
        categoryEntity.setName("categoryEntity");
        categoryEntity.setItemEntities(Arrays.asList(itemEntity));
        return categoryEntity;
    }

    public static CustomerEntity getMockCustomerEntity() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1);
        customerEntity.setName("customer name");
        customerEntity.setPhone("0917878787");
        return customerEntity;
    }
}

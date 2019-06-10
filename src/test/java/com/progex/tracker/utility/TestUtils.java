package com.progex.tracker.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progex.tracker.category.entity.Category;
import com.progex.tracker.customer.entity.Customer;
import com.progex.tracker.dining.entity.DiningTable;
import com.progex.tracker.item.entity.Item;
import com.progex.tracker.order.entity.Order;
import com.progex.tracker.order.entity.Status;

import java.util.Arrays;

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

    public static Category getMockCategory() {
        Item item = getMockItem();
        Category category = new Category();
        category.setId(1);
        category.setName("category");
        category.setItemEntities(Arrays.asList(item));
        return category;
    }

    public static Customer getMockCustomerEntity() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("customer name");
        customer.setPhone("0917878787");
        return customer;
    }

    public static Order getMockOrder() {
        Order order = new Order();
        order.setId(1);
        DiningTable table = getMockDiningTable();
        order.setDiningTable(table);
        order.setCustomer(getMockCustomerEntity());
        order.setStatus(Status.PENDING);
        Item item = getMockItem();
        order.setOrderedItems(Arrays.asList(item));
        return order;
    }

    private static DiningTable getMockDiningTable() {
        DiningTable table = new DiningTable();
        table.setId(1);
        table.setGroupNo("grp1");
        table.setNoOfSeats(6);
        return table;
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

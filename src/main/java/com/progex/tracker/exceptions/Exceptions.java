package com.progex.tracker.exceptions;

public class Exceptions {

    private Exceptions() {
    }

    public static EntityNotFoundException getCustomerNotFoundException(long customerId) {
        return new EntityNotFoundException("Customer not found for id: " + customerId);
    }

    public static EntityNotFoundException getUserNotFoundException(long userId) {
        return new EntityNotFoundException("User not found for id: " + userId);
    }

    public static RestAPIEntityNotFoundException getCategoryNotFoundException(int categoryId) {
        return new RestAPIEntityNotFoundException("CategoryEntity not found for id: " + categoryId);
    }

    public static RestAPIEntityNotFoundException getItemNotFoundException(int itemId) {
        return new RestAPIEntityNotFoundException("ItemEntity not found for id: " + itemId);
    }
}

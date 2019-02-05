package com.progex.tracker.exceptions;

public class Exceptions {

    public static EntityNotFoundException getCustomerNotFoundException(long customerId) {
        return new EntityNotFoundException("Customer not found for id: " + customerId);
    }

}

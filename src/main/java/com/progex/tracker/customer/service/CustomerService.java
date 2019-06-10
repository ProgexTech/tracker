package com.progex.tracker.customer.service;

import com.progex.tracker.customer.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer insert(Customer customer);

    Optional<Customer> getById(long customerId);

    Customer update(long customerId, Customer customer);

    List<Customer> getAll(int offset, int limit);

}

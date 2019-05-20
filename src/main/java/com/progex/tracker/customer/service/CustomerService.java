package com.progex.tracker.customer.service;

import com.progex.tracker.customer.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    CustomerEntity insert(CustomerEntity customer);

    Optional<CustomerEntity> getById(long customerId);

    CustomerEntity update(long customerId, CustomerEntity customer);

    List<CustomerEntity> getAll(int offset, int limit);

}

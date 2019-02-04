package com.progex.tracker.customer.service;

import com.progex.tracker.customer.dto.Customer;
import com.progex.tracker.customer.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {

    CustomerEntity createNewCustomer(Customer customer);

    CustomerEntity getCustomerById(long customerId);

    CustomerEntity updateCustomer(long customerId, Customer customer);

    List<CustomerEntity> getAllCustomers(int offset, int limit);

}

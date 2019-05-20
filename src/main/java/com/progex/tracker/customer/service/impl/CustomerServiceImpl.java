package com.progex.tracker.customer.service.impl;

import com.progex.tracker.customer.entity.CustomerEntity;
import com.progex.tracker.customer.repo.CustomerRepository;
import com.progex.tracker.customer.service.CustomerService;
import com.progex.tracker.exceptions.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerEntity insert(CustomerEntity customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<CustomerEntity> getById(long customerId) {

        return customerRepository.findById(customerId);
    }

    @Override
    public CustomerEntity update(long customerId, CustomerEntity customer) {
        CustomerEntity customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    LOGGER.warn("Customer not found for id: [{}].", customerId);
                    throw Exceptions.getCustomerNotFoundException(customerId);
                });

        customerEntity.setName(customer.getName());
        customerEntity.setPhone(customer.getPhone());

        /* We do not update order list here */

        CustomerEntity savedCustomer = customerRepository.save(customerEntity);

        LOGGER.info("<== Customer updated for id: [{}], name: [{}].", savedCustomer.getId(), savedCustomer.getName());

        return savedCustomer;
    }

    @Override
    public List<CustomerEntity> getAll(int offset, int limit) {
        List<CustomerEntity> customerEntities = customerRepository.findAllCustomers(offset, limit);
        return customerEntities;
    }

}

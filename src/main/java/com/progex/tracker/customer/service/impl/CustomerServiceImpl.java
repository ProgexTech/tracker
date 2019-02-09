package com.progex.tracker.customer.service.impl;

import com.progex.tracker.customer.dto.Customer;
import com.progex.tracker.customer.entity.CustomerEntity;
import com.progex.tracker.customer.repo.CustomerRepository;
import com.progex.tracker.customer.service.CustomerService;
import com.progex.tracker.exceptions.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerEntity createNewCustomer(Customer customer) {

        LOGGER.info("==> Creating a new customer. name: [{}].", customer.getName());

        CustomerEntity customerEntity = customer.toEntity();
        CustomerEntity savedCustomer = customerRepository.save(customerEntity);

        LOGGER.info("<== New customer created. name: [{}], id: [{}].", savedCustomer.getName(), savedCustomer.getId());

        return savedCustomer;
    }

    @Override
    public CustomerEntity getCustomerById(long customerId) {

        LOGGER.info("==> Retrieving a customer for id: [{}].", customerId);

        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    LOGGER.warn("### Customer not found for id: [{}].", customerId);
                    throw Exceptions.getCustomerNotFoundException(customerId);
                });

        LOGGER.info("<== Returning a customer for id: [{}].", customer.getId());

        return customer;
    }

    @Override
    public CustomerEntity updateCustomer(long customerId, Customer customer) {

        LOGGER.info("==> Updating a customer for id: [{}].", customerId);

        CustomerEntity customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    LOGGER.warn("### Customer not found for id: [{}].", customerId);
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
    public List<CustomerEntity> getAllCustomers(int offset, int limit) {

        LOGGER.info("==> Retrieving all customers by offset=[{}] and limit=[{}].", offset, limit);

        List<CustomerEntity> customerEntities = customerRepository.findAllCustomers(offset, limit);

        LOGGER.info("<== Returning [{}] customers.", customerEntities.size());

        return customerEntities;
    }

}

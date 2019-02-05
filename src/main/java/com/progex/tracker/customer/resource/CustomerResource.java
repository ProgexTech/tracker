package com.progex.tracker.customer.resource;

import com.progex.tracker.customer.dto.Customer;
import com.progex.tracker.customer.entity.CustomerEntity;
import com.progex.tracker.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CustomerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerResource.class);

    private CustomerService customerService;

    @Autowired
    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers(@RequestParam(value = "offset", required = true) int offset,
                                                          @RequestParam(value = "limit", required = true) int limit) {

        LOGGER.info("Retrieving all customers. offset=[{}] limit=[{}].", offset, limit);

        List<CustomerEntity> customerEntities = customerService.getAllCustomers(offset, limit);
        List<Customer> customers = customerEntities.stream().map(Customer::toDto).collect(Collectors.toList());

        LOGGER.info("Returning all customers. count=[{}].", customerEntities.size());

        return ResponseEntity.ok(customers);
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable long customerId) {

        LOGGER.info("Retrieving a customer for id=[{}].", customerId);

        CustomerEntity customerEntity = customerService.getCustomerById(customerId);
        Customer customer = Customer.toDto(customerEntity);

        LOGGER.info("Returning a customer for id=[{}].", customerEntity.getId());

        return ResponseEntity.ok(customer);
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createNewCustomer(@Valid @RequestBody Customer customer) {

        LOGGER.info("Creating a new customer: [{}].", customer);

        CustomerEntity customerEntity = customerService.createNewCustomer(customer);
        Customer createdCustomer = Customer.toDto(customerEntity);

        LOGGER.info("New customer created successfully, id: [{}].", customerEntity.getId());

        return ResponseEntity.ok(createdCustomer);
    }

    @PutMapping("/customers")
    public ResponseEntity<Customer> updateCustomer() {
        return null;
    }

}

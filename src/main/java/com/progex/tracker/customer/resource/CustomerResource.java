package com.progex.tracker.customer.resource;

import com.progex.tracker.customer.dto.Customer;
import com.progex.tracker.customer.entity.CustomerEntity;
import com.progex.tracker.customer.service.CustomerService;
import com.progex.tracker.exceptions.Exceptions;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CustomerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerResource.class);
    private static final String BASE_URL_STR = "/api/customers/";

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers(@RequestParam(value = "offset", required = true) int offset,
                                                          @RequestParam(value = "limit", required = true) int limit) {
        List<CustomerEntity> customerEntities = customerService.getAll(offset, limit);
        if (!customerEntities.isEmpty()) {
            List<Customer> customers = customerEntities.stream().filter(Objects::nonNull)
                    .map(customerEntity ->
                            modelMapper.map(customerEntity, Customer.class)
                    ).collect(Collectors.toList());

            return ResponseEntity.ok(customers);
        }
        LOGGER.warn("No Customers found for the given offset =[{}] , limit=[{}]", offset, limit);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable long customerId) {
        return customerService.getById(customerId).map(customer -> {
                    Customer category = modelMapper.map(customer, Customer.class);
                    return ResponseEntity.ok().body(category);
                }
        ).orElseThrow(() -> {
            LOGGER.warn("No Customer found for the given id = {}", customerId);
            return Exceptions.getCustomerNotFoundException(customerId);
        });
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        if (Objects.nonNull(customer)) {
            CustomerEntity customerEntity = modelMapper.map(customer, CustomerEntity.class);
            CustomerEntity insertedCustomerEntity = customerService.insert(customerEntity);
            return ResponseEntity.created(URI.create(BASE_URL_STR +
                    insertedCustomerEntity.getId())).body(modelMapper.
                    map(insertedCustomerEntity, Customer.class));
        }
        LOGGER.warn("Null has been received to insert as customer");
        return ResponseEntity.noContent().build();
    }

}

package com.progex.tracker.customer.resource;

import com.progex.tracker.customer.dto.CustomerDto;
import com.progex.tracker.customer.entity.Customer;
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
    public ResponseEntity<List<CustomerDto>> getAllCustomers(@RequestParam(value = "offset", required = true) int offset,
                                                             @RequestParam(value = "limit", required = true) int limit) {
        List<Customer> customerEntities = customerService.getAll(offset, limit);
        if (!customerEntities.isEmpty()) {
            List<CustomerDto> customerDtos = customerEntities.stream().filter(Objects::nonNull)
                    .map(customerEntity ->
                            modelMapper.map(customerEntity, CustomerDto.class)
                    ).collect(Collectors.toList());

            return ResponseEntity.ok(customerDtos);
        }
        LOGGER.warn("No Customers found for the given offset =[{}] , limit=[{}]", offset, limit);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable long customerId) {
        return customerService.getById(customerId).map(customer -> {
                    CustomerDto category = modelMapper.map(customer, CustomerDto.class);
                    return ResponseEntity.ok().body(category);
                }
        ).orElseThrow(() -> {
            LOGGER.warn("No CustomerDto found for the given id = {}", customerId);
            return Exceptions.getCustomerNotFoundException(customerId);
        });
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDTo) {
        if (Objects.nonNull(customerDTo)) {
            Customer customer = modelMapper.map(customerDTo, Customer.class);
            Customer insertedCustomer = customerService.insert(customer);
            return ResponseEntity.created(URI.create(BASE_URL_STR +
                    insertedCustomer.getId())).body(modelMapper.
                    map(insertedCustomer, CustomerDto.class));
        }
        LOGGER.warn("Null has been received to insert as customerDTo");
        return ResponseEntity.noContent().build();
    }

}

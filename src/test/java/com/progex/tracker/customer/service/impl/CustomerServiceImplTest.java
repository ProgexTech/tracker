package com.progex.tracker.customer.service.impl;

import com.progex.tracker.customer.dto.Customer;
import com.progex.tracker.customer.entity.CustomerEntity;
import com.progex.tracker.customer.repo.CustomerRepository;
import com.progex.tracker.exceptions.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    public void shouldReturnCustomerWhenSaving() {
        Customer customer = new Customer() {{
            this.setName("John");
            this.setPhone("0778909854");
            this.setOrderList(new ArrayList<>());
        }};

        when(customerRepository.save(customer.toEntity()))
                .thenReturn(customer.toEntity());

        CustomerEntity customerEntity = customerService.createNewCustomer(customer);
        assertEquals(customer.getName(), customerEntity.getName());
        assertEquals(customer.getPhone(), customerEntity.getPhone());
        assertEquals(0, customerEntity.getOrderList().size());
    }

    @Test
    public void shouldReturnValidCustomerWhenCallingGetByIdWithAValidId() {
        CustomerEntity testEntity = new CustomerEntity() {{
            this.setId(1L);
            this.setName("John");
            this.setPhone("0778909854");
            this.setOrderList(new ArrayList<>());
        }};

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(testEntity));

        CustomerEntity customerEntity = customerService.getCustomerById(1L);
        assertEquals(testEntity.getId(), customerEntity.getId());
        assertEquals(testEntity.getName(), customerEntity.getName());
        assertEquals(testEntity.getPhone(), customerEntity.getPhone());
        assertEquals(0, customerEntity.getOrderList().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenCallingGetByIdWithAnInvalidId() {
        CustomerEntity testEntity = new CustomerEntity() {{
            this.setId(1L);
            this.setName("John");
            this.setPhone("0778909854");
            this.setOrderList(new ArrayList<>());
        }};

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(testEntity));

        customerService.getCustomerById(2L);
    }

    @Test
    public void shouldReturnUpdatedEntityWhenCallingUpdateWithAValidId() {
        Customer customer = new Customer() {{
            this.setName("John Doe");
            this.setPhone("0112258796");
            this.setOrderList(new ArrayList<>());
        }};
        CustomerEntity testEntity = new CustomerEntity() {{
            this.setId(1L);
            this.setName(customer.getName());
            this.setPhone(customer.getPhone());
            this.setOrderList(new ArrayList<>());
        }};

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(testEntity));
        when(customerRepository.save(testEntity))
                .thenReturn(testEntity);

        CustomerEntity customerEntity = customerService.updateCustomer(1L, customer);
        assertEquals(testEntity.getId(), customerEntity.getId());
        assertEquals(testEntity.getName(), customerEntity.getName());
        assertEquals(testEntity.getPhone(), customerEntity.getPhone());
        assertEquals(0, customerEntity.getOrderList().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenCallingUpdateWithAnInvalidId() {
        Customer customer = new Customer() {{
            this.setName("John Doe");
            this.setPhone("0112258796");
            this.setOrderList(new ArrayList<>());
        }};
        CustomerEntity testEntity = new CustomerEntity() {{
            this.setId(1L);
            this.setName(customer.getName());
            this.setPhone(customer.getPhone());
            this.setOrderList(new ArrayList<>());
        }};

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(testEntity));
        when(customerRepository.save(testEntity))
                .thenReturn(testEntity);

        customerService.updateCustomer(2L, customer);
    }

    @Test
    public void shouldReturnCustomerListWhenCallingGetAllCustomers() {
        when(customerRepository.findAllCustomers(0, 10))
                .thenReturn(Arrays.asList(
                        new CustomerEntity(),
                        new CustomerEntity(),
                        new CustomerEntity()
                ));

        List<CustomerEntity> customerEntities = customerService.getAllCustomers(0, 10);
        assertEquals(3, customerEntities.size());
    }

}

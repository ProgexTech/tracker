package com.progex.tracker.customer.service.impl;

import com.progex.tracker.customer.entity.Customer;
import com.progex.tracker.customer.repo.CustomerRepository;
import com.progex.tracker.utility.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    public void shouldReturnCustomerWhenSaving() {
        Customer customer = TestUtils.getMockCustomerEntity();

        when(customerRepository.save(customer))
                .thenReturn(customer);

        Customer customerEntity = customerService.insert(customer);
        assertEquals(customer.getName(), customerEntity.getName());
        assertEquals(customer.getPhone(), customerEntity.getPhone());
    }

    @Test
    public void shouldReturnValidCustomerWhenCallingGetByIdWithAValidId() {
        Customer testEntity = TestUtils.getMockCustomerEntity();

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(testEntity));

        Optional<Customer> customerEntity = customerService.getById(1L);
        assertTrue(customerEntity.isPresent());
        assertEquals(testEntity.getId(), customerEntity.get().getId());
        assertEquals(testEntity.getName(), customerEntity.get().getName());
        assertEquals(testEntity.getPhone(), customerEntity.get().getPhone());
        assertNull(customerEntity.get().getOrders());
    }

    @Test
    public void shouldThrowExceptionWhenCallingGetByIdWithAnInvalidId() {
        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());
        assertTrue(customerService.getById(1L).isEmpty());
    }

    @Test
    public void shouldReturnUpdatedEntityWhenCallingUpdateWithAValidId() {
        Customer customer = TestUtils.getMockCustomerEntity();

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));
        when(customerRepository.save(customer))
                .thenReturn(customer);

        Customer updatedCustomer = customerService.update(1L, customer);
        assertEquals(updatedCustomer.getId(), updatedCustomer.getId());
        assertEquals(updatedCustomer.getName(), updatedCustomer.getName());
        assertEquals(updatedCustomer.getPhone(), updatedCustomer.getPhone());
    }

    @Test
    public void shouldReturnCustomerListWhenCallingGetAllCustomers() {
        when(customerRepository.findAllCustomers(0, 10))
                .thenReturn(Arrays.asList(
                        new Customer(),
                        new Customer(),
                        new Customer()
                ));

        List<Customer> customerEntities = customerService.getAll(0, 10);
        assertEquals(3, customerEntities.size());
    }

}

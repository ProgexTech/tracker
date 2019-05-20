package com.progex.tracker.customer.service.impl;

import com.progex.tracker.customer.entity.CustomerEntity;
import com.progex.tracker.customer.repo.CustomerRepository;
import com.progex.tracker.exceptions.EntityNotFoundException;
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
        CustomerEntity customer = TestUtils.getMockCustomerEntity();

        when(customerRepository.save(customer))
                .thenReturn(customer);

        CustomerEntity customerEntity = customerService.insert(customer);
        assertEquals(customer.getName(), customerEntity.getName());
        assertEquals(customer.getPhone(), customerEntity.getPhone());
    }

    @Test
    public void shouldReturnValidCustomerWhenCallingGetByIdWithAValidId() {
        CustomerEntity testEntity = TestUtils.getMockCustomerEntity();

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(testEntity));

        Optional<CustomerEntity> customerEntity = customerService.getById(1L);
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
        CustomerEntity customerEntity = TestUtils.getMockCustomerEntity();

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customerEntity));
        when(customerRepository.save(customerEntity))
                .thenReturn(customerEntity);

        CustomerEntity updatedCustomerEntity = customerService.update(1L, customerEntity);
        assertEquals(updatedCustomerEntity.getId(), updatedCustomerEntity.getId());
        assertEquals(updatedCustomerEntity.getName(), updatedCustomerEntity.getName());
        assertEquals(updatedCustomerEntity.getPhone(), updatedCustomerEntity.getPhone());
    }

    @Test
    public void shouldReturnCustomerListWhenCallingGetAllCustomers() {
        when(customerRepository.findAllCustomers(0, 10))
                .thenReturn(Arrays.asList(
                        new CustomerEntity(),
                        new CustomerEntity(),
                        new CustomerEntity()
                ));

        List<CustomerEntity> customerEntities = customerService.getAll(0, 10);
        assertEquals(3, customerEntities.size());
    }

}

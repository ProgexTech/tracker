package com.progex.tracker.customer.repo;

import com.progex.tracker.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT * FROM customers LIMIT ?2 OFFSET ?1", nativeQuery = true)
    List<Customer> findAllCustomers(int offset, int limit);

}

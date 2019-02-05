package com.progex.tracker.customer.repo;

import com.progex.tracker.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query(value = "SELECT * FROM customers LIMIT ?2 OFFSET ?1", nativeQuery = true)
    List<CustomerEntity> findAllCustomers(int offset, int limit);

}

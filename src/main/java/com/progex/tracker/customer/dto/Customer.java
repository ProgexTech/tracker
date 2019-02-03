package com.progex.tracker.customer.dto;

import com.progex.tracker.customer.entity.CustomerEntity;
import com.progex.tracker.order.dto.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class Customer {

    private long id;
    private String name;
    private String phone;
    private List<Order> orderList;

    public static Customer toDto(CustomerEntity customerEntity) {
        Customer customer = new Customer();

        customer.setId(customerEntity.getId());
        customer.setName(customerEntity.getName());
        customer.setPhone(customerEntity.getPhone());
        customer.setOrderList(Optional.ofNullable(customerEntity.getOrderList())
                .map(orderEntities ->
                        orderEntities.stream().map(Order::toDto).collect(Collectors.toList()))
                .orElse(new ArrayList<>()));

        return customer;
    }

    public static List<Customer> toDto(List<CustomerEntity> customerEntities) {
        return customerEntities.stream()
                .map(Customer::toDto)
                .collect(Collectors.toList());
    }

    public CustomerEntity toEntity() {
        CustomerEntity customerEntity = new CustomerEntity();

        customerEntity.setId(this.id);
        customerEntity.setName(this.name);
        customerEntity.setPhone(this.phone);
        customerEntity.setOrderList(Optional.ofNullable(this.orderList)
                .map(orders ->
                        orders.stream().map(Order::toEntity).collect(Collectors.toList()))
                .orElse(new ArrayList<>()));

        return customerEntity;
    }

}

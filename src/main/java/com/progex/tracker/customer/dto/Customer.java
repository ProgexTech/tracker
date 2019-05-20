package com.progex.tracker.customer.dto;

import com.progex.tracker.order.dto.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private long id;
    private String name;
    private String phone;
    private List<Order> orderList;
}

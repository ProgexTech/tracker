package com.progex.tracker.order.service.impl;

import com.progex.tracker.order.entity.Order;
import com.progex.tracker.order.repo.OrderRepository;
import com.progex.tracker.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public Order insert(Order order) {
        return orderRepository.save(order);
    }
}


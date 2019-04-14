package com.progex.tracker.order.service.impl;

import com.progex.tracker.order.entity.OrderEntity;
import com.progex.tracker.order.repo.OrderRepository;
import com.progex.tracker.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public OrderEntity insert(OrderEntity orderEntity) {
        return orderRepository.save(orderEntity);
    }
}


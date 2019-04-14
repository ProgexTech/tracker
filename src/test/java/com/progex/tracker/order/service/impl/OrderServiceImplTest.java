package com.progex.tracker.order.service.impl;

import com.progex.tracker.customer.entity.CustomerEntity;
import com.progex.tracker.dining.entity.DiningTableEntity;
import com.progex.tracker.order.entity.OrderEntity;
import com.progex.tracker.order.repo.OrderRepository;
import com.progex.tracker.user.entity.Cook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class OrderServiceImplTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void test(){
        OrderEntity orderEntity = new OrderEntity();
        Cook cook = new Cook();
        cook.setId(1);
        orderEntity.setCook(cook);
        CustomerEntity cutomerEntity = new CustomerEntity();
        cutomerEntity.setId(1);
        orderEntity.setCustomerEntity(cutomerEntity);
        DiningTableEntity diningTbl = new DiningTableEntity();
        diningTbl.setId(1);
        orderEntity.setDiningTable(diningTbl);
        OrderEntity saved = orderRepository.save(orderEntity);
        assertNotNull(saved);
    }

}
package com.progex.tracker.order.service.impl;

import com.progex.tracker.customer.entity.Customer;
import com.progex.tracker.dining.entity.DiningTable;
import com.progex.tracker.order.entity.Order;
import com.progex.tracker.order.repo.OrderRepository;
import com.progex.tracker.user.entity.Cook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class OrderServiceImplTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void test(){
        Order order = new Order();
        Cook cook = new Cook();
        cook.setId(1);
        order.setCook(cook);
        Customer cutomerEntity = new Customer();
        cutomerEntity.setId(1);
        order.setCustomer(cutomerEntity);
        DiningTable diningTbl = new DiningTable();
        diningTbl.setId(1);
        order.setDiningTable(diningTbl);
        Order saved = orderRepository.save(order);
        //assertNotNull(saved);
    }

}
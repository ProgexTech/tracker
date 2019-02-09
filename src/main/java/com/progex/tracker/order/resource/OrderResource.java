package com.progex.tracker.order.resource;

import com.progex.tracker.order.dto.Order;
import com.progex.tracker.order.entity.OrderEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class OrderResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderResource.class);

    public OrderResource() {

    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createNewUser(@Valid @RequestBody Order order) {

        LOGGER.info("Creating a new order for table: [{}].", order.getDiningTable().getId());

        OrderEntity orderEntity = new OrderEntity();
        Order createdOrder = Order.toDto(orderEntity);

        LOGGER.info("New order created successfully, id: [{}].", orderEntity.getId());

        return ResponseEntity.ok(createdOrder);

    }


}

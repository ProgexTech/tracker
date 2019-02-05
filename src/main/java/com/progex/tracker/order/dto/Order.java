package com.progex.tracker.order.dto;

import com.progex.tracker.customer.dto.Customer;
import com.progex.tracker.entity.Item;
import com.progex.tracker.order.entity.OrderEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class Order {

    private long id;
    private List<Item> itemList;
    private Customer customer;
    private LocalDateTime orderDate;

    public static Order toDto(OrderEntity orderEntity) {
        Order order = new Order();

        order.setId(orderEntity.getId());
        order.setCustomer(Customer.toDto(orderEntity.getCustomer()));
        order.setItemList(orderEntity.getItemList()); // TODO: Change to DTO
        order.setOrderDate(orderEntity.getOrderDate());

        return order;
    }

    public static List<Order> toDto(List<OrderEntity> orderEntities) {
        return orderEntities.stream()
                .map(Order::toDto)
                .collect(Collectors.toList());
    }

    public OrderEntity toEntity() {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setId(this.id);
        orderEntity.setItemList(this.itemList);
        orderEntity.setCustomer(this.customer.toEntity());
        orderEntity.setOrderDate(this.orderDate);

        return orderEntity;
    }
}

package com.progex.tracker.order.dto;

import com.progex.tracker.customer.dto.Customer;
import com.progex.tracker.diningTable.dto.DiningTable;
import com.progex.tracker.item.entity.Item;
import com.progex.tracker.order.entity.OrderEntity;
import com.progex.tracker.orderItem.dto.OrderItem;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private long id;
    private List<OrderItem> orderItems;
    @NotNull
    private DiningTable diningTable;
    private LocalDateTime createdTime;

    public static Order toDto(OrderEntity orderEntity) {
        Order order = new Order();

        order.setId(orderEntity.getId());
        order.setDiningTable(DiningTable.toDto(orderEntity.getDiningTable()));
        order.setOrderItems(Optional.ofNullable(orderEntity.getOrderItems())
                .map(OrderItem::toDto).orElse(new ArrayList<>()));
        order.setCreatedTime(orderEntity.getCreatedTime());

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
        orderEntity.setOrderItems(this.orderItems.stream()
                .map(OrderItem::toEntity).collect(Collectors.toList()));
        orderEntity.setDiningTable(this.diningTable.toEntity());
        orderEntity.setCreatedTime(this.createdTime);

        return orderEntity;
    }
}

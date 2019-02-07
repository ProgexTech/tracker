package com.progex.tracker.orderItem.dto;

import com.progex.tracker.item.dto.ItemDTO;
import com.progex.tracker.order.dto.Order;
import com.progex.tracker.orderItem.entity.OrderItemEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    private long id;
    private double quantity;
    private ItemDTO item;
    private Order order;
    private LocalDateTime createdTime;

    public static OrderItem toDto(OrderItemEntity orderItemEntity) {
        OrderItem orderItem = new OrderItem();

        orderItem.setId(orderItemEntity.getId());
        //orderItem.setItem(Optional.ofNullable(orderItemEntity.getItem()).map(ItemDTO).orElse(null));
        orderItem.setOrder(Optional.ofNullable(orderItemEntity.getOrderEntity())
                .map(Order::toDto).orElse(null));
        orderItem.setCreatedTime(orderItemEntity.getCreatedTime());

        return orderItem;
    }

    public static List<OrderItem> toDto(List<OrderItemEntity> orderItemEntities) {
        return orderItemEntities.stream()
                .map(OrderItem::toDto).collect(Collectors.toList());
    }

    public OrderItemEntity toEntity() {
        OrderItemEntity orderItemEntity = new OrderItemEntity();

        //orderItemEntity.setItem();
        orderItemEntity.setOrderEntity(this.order.toEntity());
        orderItemEntity.setCreatedTime(this.createdTime);

        return orderItemEntity;
    }

}

package com.progex.tracker.order.dto;

import com.progex.tracker.dining.dto.DiningTable;
import com.progex.tracker.item.dto.Item;
import com.progex.tracker.order.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private long id;
    private List<Item> orderItems;
    @NotNull
    private DiningTable diningTable;
    private LocalDateTime createdTime;

    public static Order toDto(OrderEntity orderEntity) {
        Order order = new Order();

       /* order.setId(orderEntity.getId());
        order.setDiningTable(DiningTable.toDto(orderEntity.getDiningTable()));
        order.setOrderItems(Optional.ofNullable(orderEntity.getOrderItems())
                .map(OrderItem::toDto).orElse(new ArrayList<>()));
        order.setCreatedTime(orderEntity.getCreatedTime());*/

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
       /* orderEntity.setOrderItems(this.orderItems.stream()
                .map(OrderItem::toEntity).collect(Collectors.toList()));
        orderEntity.setDiningTable(this.dining.toEntity());
        orderEntity.setCreatedTime(this.createdTime);*/

        return orderEntity;
    }
}

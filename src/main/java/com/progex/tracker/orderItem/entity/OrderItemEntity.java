package com.progex.tracker.orderItem.entity;

import com.progex.tracker.item.entity.Item;
import com.progex.tracker.order.entity.OrderEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    private Item item;

    @ManyToOne
    private OrderEntity orderEntity;

    private double quantity;
    private LocalDateTime createdTime;

}

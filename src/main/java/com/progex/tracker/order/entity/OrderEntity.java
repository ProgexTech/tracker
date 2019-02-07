package com.progex.tracker.order.entity;

import com.progex.tracker.diningTable.entity.DiningTableEntity;
import com.progex.tracker.orderItem.entity.OrderItemEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @OneToMany
    private List<OrderItemEntity> orderItems;

    @ManyToOne
    private DiningTableEntity diningTable;

    private LocalDateTime createdTime;

}

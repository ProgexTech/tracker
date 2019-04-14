package com.progex.tracker.order.entity;

import com.progex.tracker.customer.entity.CustomerEntity;
import com.progex.tracker.dining.entity.DiningTableEntity;
import com.progex.tracker.item.entity.ItemEntity;
import com.progex.tracker.user.entity.Cook;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToMany
    @JoinTable(
            name = "order_item",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<ItemEntity> orderedItems;

    @ManyToOne(fetch = FetchType.LAZY)
    private DiningTableEntity diningTable;

    @ManyToOne(fetch = FetchType.LAZY)
    private CustomerEntity customerEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cook cook;

    private LocalDateTime createdTime;

    @Column(length = 32, columnDefinition = "varchar(32) default 'PENDING'")
    @Enumerated(value = EnumType.STRING)
    private Status status;

}

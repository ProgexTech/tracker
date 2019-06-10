package com.progex.tracker.order.entity;

import com.progex.tracker.customer.entity.Customer;
import com.progex.tracker.dining.entity.DiningTable;
import com.progex.tracker.item.entity.Item;
import com.progex.tracker.user.entity.Cook;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Order_Detail")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToMany
    @JoinTable(
            name = "order_item",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Item> orderedItems;

    @ManyToOne(fetch = FetchType.LAZY)
    private DiningTable diningTable;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cook cook;

    private LocalDateTime createdTime;

    @Column(length = 32, columnDefinition = "varchar(32) default 'PENDING'")
    @Enumerated(value = EnumType.STRING)
    private Status status;

}

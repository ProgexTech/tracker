package com.progex.tracker.order.entity;

import com.progex.tracker.customer.entity.CustomerEntity;
import com.progex.tracker.item.entity.Item;
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

    @ManyToMany
    private List<Item> itemList;

    @ManyToOne
    private CustomerEntity customer;

    private LocalDateTime orderDate;

}

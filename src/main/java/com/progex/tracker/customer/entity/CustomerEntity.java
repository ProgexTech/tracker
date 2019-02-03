package com.progex.tracker.customer.entity;

import com.progex.tracker.order.entity.OrderEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String phone;

    @OneToMany
    private List<OrderEntity> orderList;

}

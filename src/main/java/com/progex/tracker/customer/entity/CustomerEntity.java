package com.progex.tracker.customer.entity;

import com.progex.tracker.order.entity.OrderEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customerEntity")
    private List<OrderEntity> orders;

    private String name;
    private String phone;

}

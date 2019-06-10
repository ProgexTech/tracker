package com.progex.tracker.dining.entity;

import com.progex.tracker.order.entity.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class DiningTable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "diningTable")
    private List<Order> orders;

    private int noOfSeats;

    @NotNull
    private String groupNo;

}

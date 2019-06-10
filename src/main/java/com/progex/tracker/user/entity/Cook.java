package com.progex.tracker.user.entity;

import com.progex.tracker.order.entity.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Cook extends User {

    @OneToMany(fetch= FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cook")
    private List<Order> orders;

    private String expertise;
}

package com.progex.tracker.user.entity;

import com.progex.tracker.order.entity.OrderEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Cook extends UserEntity{

    @OneToMany(fetch= FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cook")
    private List<OrderEntity> orders;

    private String experties;
}

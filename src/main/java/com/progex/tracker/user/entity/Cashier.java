package com.progex.tracker.user.entity;


import com.progex.tracker.bill.entity.Bill;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Cashier extends User {

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cashier")
    private List<Bill> bills;
}

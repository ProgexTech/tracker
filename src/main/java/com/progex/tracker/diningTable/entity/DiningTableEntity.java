package com.progex.tracker.diningTable.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dining_tables")
@Data
@NoArgsConstructor
public class DiningTableEntity {

    @NotNull
    private String id;

    @NotNull
    private int noOfSeats;

    @NotNull
    private String group;

}

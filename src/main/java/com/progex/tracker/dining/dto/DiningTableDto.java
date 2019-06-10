package com.progex.tracker.dining.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiningTableDto {

    private int id;
    private String groupNo;
    private int noOfSeats;
}

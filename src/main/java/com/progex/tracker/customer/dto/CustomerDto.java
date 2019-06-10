package com.progex.tracker.customer.dto;

import com.progex.tracker.order.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private long id;
    private String name;
    private String phone;
    private List<OrderDto> orderDtoList;
}

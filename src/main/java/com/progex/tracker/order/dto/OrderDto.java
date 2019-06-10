package com.progex.tracker.order.dto;

import com.progex.tracker.dining.dto.DiningTableDto;
import com.progex.tracker.item.dto.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private long id;
    private List<ItemDto> orderItemDtos;
    @NotNull
    private DiningTableDto diningTableDto;
    private LocalDateTime createdTime;

}

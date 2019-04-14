package com.progex.tracker.dining.dto;

import com.progex.tracker.dining.entity.DiningTableEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiningTable {

    private int id;
    private String groupNo;
    private int noOfSeats;

    public static DiningTable toDto(DiningTableEntity diningTableEntity) {
        DiningTable diningTable = new DiningTable();

        diningTable.setId(diningTableEntity.getId());
        diningTable.setGroupNo(diningTableEntity.getGroupNo());
        diningTable.setNoOfSeats(diningTableEntity.getNoOfSeats());

        return diningTable;
    }

    public static List<DiningTable> toDto(List<DiningTableEntity> diningTableEntities) {
        return diningTableEntities.stream()
                .map(DiningTable::toDto)
                .collect(Collectors.toList());
    }

    public DiningTableEntity toEntity() {
        DiningTableEntity diningTableEntity = new DiningTableEntity();

        diningTableEntity.setId(this.id);
        diningTableEntity.setGroupNo(this.groupNo);
        diningTableEntity.setNoOfSeats(this.noOfSeats);

        return diningTableEntity;
    }

}

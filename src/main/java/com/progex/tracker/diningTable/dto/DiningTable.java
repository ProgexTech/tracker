package com.progex.tracker.diningTable.dto;

import com.progex.tracker.diningTable.entity.DiningTableEntity;
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

    private String id;
    private String group;
    private int noOfSeats;

    public static DiningTable toDto(DiningTableEntity diningTableEntity) {
        DiningTable diningTable = new DiningTable();

        diningTable.setId(diningTableEntity.getId());
        diningTable.setGroup(diningTableEntity.getGroup());
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
        diningTableEntity.setGroup(this.group);
        diningTableEntity.setNoOfSeats(this.noOfSeats);

        return diningTableEntity;
    }

}

package com.progex.tracker.category.entity;

import com.progex.tracker.item.entity.ItemEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author indunil
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private @NonNull String name;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "categoryEntity")
    private List<ItemEntity> itemEntities = new ArrayList<>();

    public void addItem(ItemEntity itemEntity) {
        itemEntities.add(itemEntity);
        if (itemEntity.getCategoryEntity() != this){
            itemEntity.setCategoryEntity(this);
        }
    }
}

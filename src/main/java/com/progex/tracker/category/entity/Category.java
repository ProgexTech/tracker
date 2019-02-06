package com.progex.tracker.category.entity;

import com.progex.tracker.item.entity.Item;
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
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private @NonNull String name;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "category")
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
        if (item.getCategory() != this){
            item.setCategory(this);
        }
    }
}

package com.progex.tracker.item.entity;

import com.progex.tracker.category.entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

/**
 * @author indunil
 */
@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private @NonNull String name;
    private String calorie;
    private String origin;
    private String description;
    private String code;
    @Lob
    private byte[] picture;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}

package com.progex.tracker.item.entity;

import com.progex.tracker.category.entity.Category;
import lombok.*;

import javax.persistence.*;

/**
 * @author indunil
 */
@Entity
@Table(name = "item")
@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}

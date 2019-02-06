package com.progex.tracker.item.entity;

import com.progex.tracker.category.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

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
    private boolean isAvailable;
    private int pax;
    @Column(name="price", columnDefinition="Decimal(10,2) default '0.00'")
    private BigDecimal price;
    @Lob
    private byte[] picture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}

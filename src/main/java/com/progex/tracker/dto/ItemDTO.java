package com.progex.tracker.dto;

import com.progex.tracker.entity.Category;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public class ItemDTO {
    @Id
    private int id;
    @NotNull
    private Category category;
    private @NonNull String name;
    private String calorie;
    private String origin;
    private String description;
    private String code;
    private byte[] picture;
}

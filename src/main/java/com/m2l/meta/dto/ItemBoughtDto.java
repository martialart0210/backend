package com.m2l.meta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ItemBoughtDto {
    @JsonProperty
    List<DrawerItemDto> drawerItems;
    @JsonProperty
    private List<WardrobeItemDto> wardrobeItems ;
}

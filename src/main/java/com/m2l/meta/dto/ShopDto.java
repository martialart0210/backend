package com.m2l.meta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopDto {
    @JsonProperty
    Integer shopType;
    @JsonProperty
    List<ItemDto> listItem;
}

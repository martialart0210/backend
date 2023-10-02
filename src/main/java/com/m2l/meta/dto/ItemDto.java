package com.m2l.meta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    @JsonProperty
    private Long itemId;
    @JsonProperty
    private Integer gender;
    @JsonProperty
    private String itemName;
    @JsonProperty
    private String itemDescription;
    @JsonProperty
    private Integer price;
    @JsonProperty
    private boolean isSoldOut;
    @JsonProperty
    private Integer itemType;
    @JsonProperty
    private Integer length;
    @JsonProperty
    private Integer width;
    @JsonProperty
    private Integer height;
}

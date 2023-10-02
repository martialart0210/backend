package com.m2l.meta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CostumeDto {
    @JsonProperty
    private Long costumeId;
    @JsonProperty
    private Integer costumeType;
}

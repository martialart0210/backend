package com.m2l.meta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ContactInfoDto {
    @JsonProperty
    private Long characterId;
    @JsonProperty
    private String characterName;
    @JsonProperty
    @Schema(description = "GENDER VALUE: MALE , FEMALE")
    private String characterGender;
}

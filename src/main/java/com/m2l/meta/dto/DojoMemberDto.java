package com.m2l.meta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DojoMemberDto {
    @JsonProperty
    private Long memberId;
    @JsonProperty
    private Long characterId;
    @JsonProperty
    private String characterName;
    @JsonProperty
    private String lastAccessTime;
    @JsonProperty
    @Schema(description = "GENDER VALUE: MALE , FEMALE")
    private String characterGender;
    @JsonProperty
    @Schema(description = "POSITION VALUE: INSTRUCTOR , SUB_INSTRUCTOR , TRAINEE")
    private String memberPosition;
}

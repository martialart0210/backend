package com.m2l.meta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema
public class DojoDto {
    @JsonProperty
    @Schema
    private Long dojoId;
    @JsonProperty
    @Parameter(required = true)
    private String dojoName;
    @JsonProperty
    @Schema(description = "Subscription Type : FREE , APPROVED")
    private String subscriptionType;
    @JsonProperty
    private String introduction;
    @JsonProperty
    private String dojoNotice;
    @JsonProperty
    private Long symbolId;
    @JsonProperty
    private String dojoColorCode;
    @JsonProperty
    private Integer memberLimit;
    @JsonProperty
    private List<DojoMemberDto> currentMember;
    @JsonProperty
    private List<DojoRequestDto> requestDtos;
    @JsonProperty
    private List<DojoLogDto> dojoLogDtos;
}

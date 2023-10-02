package com.m2l.meta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CharacterInfoDto {
    @JsonProperty
    private Long characterId;
    @JsonProperty
    private String characterName;
    @JsonProperty
    private String dojoPosition;
    @JsonProperty
    private String dojoName;
    @JsonProperty
    @Schema(description = "GENDER VALUE: MALE , FEMALE")
    private String characterGender;
    @JsonProperty
    private String characterModel;
    @JsonProperty
    private Long costumeHair;
    @JsonProperty
    private Long costumeTop;
    @JsonProperty
    private Long costumeShoe;
    @JsonProperty
    private Long costumeBottom;
    @JsonProperty
    private Long costumeAccessory;
    @JsonProperty
    private Integer gold;
    @JsonProperty
    private Long roomId;
    @JsonProperty
    private Integer roomLevel;
    @JsonProperty
    private Integer expandCoupon;
    @JsonProperty
    private boolean isCompletedQuests;
    @JsonProperty
    private boolean isCompletedGames;
    @JsonProperty
    private String aboutMe;
    @JsonProperty
    List<ContactInfoDto> listFriend;
    @JsonProperty
    List<ContactInfoDto> listBlock;
}

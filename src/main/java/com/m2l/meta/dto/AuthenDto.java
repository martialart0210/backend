package com.m2l.meta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenDto {
    @JsonProperty("accessToken")
    private String accessToken;
    @JsonProperty("refreshToken")
    private String refreshToken;
    @JsonProperty("prefix")
    private String prefix;
    @JsonProperty("userInfo")
    private UserDto userDto;

}

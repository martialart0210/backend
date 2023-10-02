package com.mar.meta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @JsonProperty("Id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @NotBlank(message = "Firstname cannot be blank")
    @JsonProperty("nickname")
    private String nickname;

    @NotBlank(message = "Lastname cannot be blank")
    @JsonProperty("name")
    private String name;

    @Email(message = "Email invalid")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Phone numbers cannot be blank")
    @JsonProperty("phone")
    private String phone;

    @NotBlank
    @JsonProperty("gender")
    private Character gender;

    @NotBlank
    @JsonProperty("ageRange")
    private String ageRange;

    @NotBlank
    @JsonProperty("facebookId")
    private String facebookId;

    @NotBlank
    @JsonProperty("googleId")
    private String googleId;

    @NotBlank
    @JsonProperty("kakaoId")
    private String kakaoId;

    @NotBlank
    @JsonProperty("naverId")
    private String naverId;
}

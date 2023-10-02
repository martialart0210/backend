package com.m2l.meta.config.oauth;
import java.util.Map;

public class KakaoOauth2UserInfo extends OAuth2UserInfo {
    public KakaoOauth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        return (String) ((Map) attributes.get("properties")).get("nickname");
    }

    @Override
    public String getEmail() {
        return (String) ((Map) attributes.get("kakao_account")).get("email");
    }

    @Override
    public String getPhone() {
        return (String) ((Map) attributes.get("kakao_account")).get("phone");
    }

    @Override
    public String getImageUrl() {
        return (String) ((Map) attributes.get("properties")).get("profile_image");
    }
}

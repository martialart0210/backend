package com.m2l.meta.config.oauth;

import java.util.Map;

public class NaverOauth2UserInfo extends OAuth2UserInfo {
    public NaverOauth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) ((Map) attributes.get("response")).get("id");
    }

    @Override
    public String getName() {
        return (String) ((Map) attributes.get("response")).get("name");
    }

    @Override
    public String getEmail() {
        return (String) ((Map) attributes.get("response")).get("email");
    }

    @Override
    public String getPhone() {
        return (String) ((Map) attributes.get("response")).get("phone");
    }

    @Override
    public String getImageUrl() {
        return (String) ((Map) attributes.get("response")).get("profile_image");
    }
}

package com.m2l.meta.config.oauth;


import com.m2l.meta.enum_class.AuthProvider;
import com.m2l.meta.exceptions.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.apple.toString())) {
            return new AppleOauth2UserInfo(attributes);
        }else if (registrationId.equalsIgnoreCase(AuthProvider.kakao.toString())) {
            return new KakaoOauth2UserInfo(attributes);
        }else if (registrationId.equalsIgnoreCase(AuthProvider.naver.toString())) {
            return new NaverOauth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}

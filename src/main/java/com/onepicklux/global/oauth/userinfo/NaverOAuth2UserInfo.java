package com.onepicklux.global.oauth.userinfo;

import java.util.Map;

public class NaverOAuth2UserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;
    private Map<String, Object> attributesResponse;

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.attributesResponse = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getProviderId() {
        return (String) attributesResponse.get("id");
    }

    @Override
    public String getProvider() {
        return "NAVER";
    }

    @Override
    public String getEmail() {
        return (String) attributesResponse.get("email");
    }

    @Override
    public String getName() {
        return (String) attributesResponse.get("name");
    }
}
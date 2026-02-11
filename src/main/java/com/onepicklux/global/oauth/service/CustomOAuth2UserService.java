package com.onepicklux.global.oauth.service;

import com.onepicklux.domain.member.entity.AuthProvider;
import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.domain.member.entity.Role;
import com.onepicklux.domain.member.repository.MemberRepository;
import com.onepicklux.global.oauth.userinfo.GoogleOAuth2UserInfo;
import com.onepicklux.global.oauth.userinfo.KakaoOAuth2UserInfo;
import com.onepicklux.global.oauth.userinfo.NaverOAuth2UserInfo;
import com.onepicklux.global.oauth.userinfo.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;
        if (registrationId.equals("kakao")) {
            oAuth2UserInfo = new KakaoOAuth2UserInfo(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2UserInfo = new GoogleOAuth2UserInfo(oAuth2User.getAttributes());
        } else if (registrationId.equals("naver")) {
            oAuth2UserInfo = new NaverOAuth2UserInfo(oAuth2User.getAttributes());
        } else {
            log.error("지원하지 않는 소셜 로그인입니다.");
        }

        Member member = saveOrUpdate(oAuth2UserInfo);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                oAuth2User.getAttributes(),
                userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName()
        );
    }

    private Member saveOrUpdate(OAuth2UserInfo attributes) {
        Optional<Member> optionalMember = memberRepository.findByEmail(attributes.getEmail());
        Member member;

        if (optionalMember.isPresent()) {
            member = optionalMember.get();
            member.updateName(attributes.getName());
        } else {
            member = Member.builder()
                    .email(attributes.getEmail())
                    .name(attributes.getName())
                    .role(Role.USER)
                    .provider(AuthProvider.valueOf(attributes.getProvider()))
                    .providerId(attributes.getProviderId())
                    .build();
            memberRepository.save(member);
        }
        return member;
    }
}
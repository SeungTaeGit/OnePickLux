package com.onepicklux.domain.auth.service;

import com.onepicklux.domain.auth.dto.LoginRequest;
import com.onepicklux.domain.auth.dto.MemberResponse;
import com.onepicklux.domain.auth.dto.SignupRequest;
import com.onepicklux.domain.auth.dto.TokenResponse;
import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.domain.member.repository.MemberRepository;
import com.onepicklux.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public MemberResponse signup(SignupRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Member member = request.toEntity(passwordEncoder);

        Member savedMember = memberRepository.save(member);

        return MemberResponse.of(savedMember);
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        member.updateLastLoginAt();

        String accessToken = jwtTokenProvider.createToken(authentication);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .grantType("Bearer")
                .build();
    }
}
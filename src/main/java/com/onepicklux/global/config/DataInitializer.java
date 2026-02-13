package com.onepicklux.global.config;

import com.onepicklux.domain.member.entity.AuthProvider;
import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.domain.member.entity.Role;
import com.onepicklux.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!memberRepository.existsByEmail("admin@onepick.com")) {
            Member admin = Member.builder()
                    .email("admin@onepick.com")
                    .password(passwordEncoder.encode("1234"))
                    .name("시스템관리자")
                    .phone("010-0000-0000")
                    .role(Role.ADMIN)
                    .provider(AuthProvider.LOCAL)
                    .build();

            memberRepository.save(admin);
            System.out.println("============================================================");
            System.out.println("   [INIT] 초기 관리자 계정이 생성되었습니다.");
            System.out.println("   ID: admin@onepick.com");
            System.out.println("   PW: 1234");
            System.out.println("============================================================");
        }
    }
}
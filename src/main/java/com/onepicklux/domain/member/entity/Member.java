package com.onepicklux.domain.member.entity;

import com.onepicklux.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @Column(nullable = false)
    private String name;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberGrade grade;

    @Column(nullable = false)
    private Long totalSpent;

    @Column(nullable = false)
    private Long availablePoint;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;

    private LocalDateTime lastLoginAt;

    @Column(columnDefinition = "TEXT")
    private String adminMemo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @Builder
    public Member(String email, String password, String name, String phone,
                  Gender gender, LocalDate birthDate,
                  Role role, AuthProvider provider, String providerId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.gender = (gender != null) ? gender : Gender.UNKNOWN;
        this.birthDate = birthDate;

        this.grade = MemberGrade.BRONZE;
        this.totalSpent = 0L;
        this.availablePoint = 0L;
        this.status = MemberStatus.ACTIVE;
        this.lastLoginAt = LocalDateTime.now();

        this.role = (role != null) ? role : Role.USER;
        this.provider = (provider != null) ? provider : AuthProvider.LOCAL;
        this.providerId = providerId;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateLastLoginAt() {
        this.lastLoginAt = LocalDateTime.now();
    }

    public void addTotalSpent(Long amount) {
        this.totalSpent += amount;
        updateGrade();
    }

    private void updateGrade() {
        if (this.totalSpent >= MemberGrade.VIP.getMinTotalSpent()) {
            this.grade = MemberGrade.VIP;
        } else if (this.totalSpent >= MemberGrade.GOLD.getMinTotalSpent()) {
            this.grade = MemberGrade.GOLD;
        } else if (this.totalSpent >= MemberGrade.SILVER.getMinTotalSpent()) {
            this.grade = MemberGrade.SILVER;
        }
    }

    public void withdraw() {
        this.status = MemberStatus.WITHDRAWN;
        this.name = "탈퇴한사용자";
        this.phone = null;
    }

    public void updateAdminMemo(String memo) {
        this.adminMemo = memo;
    }

    public void suspend() {
        this.status = MemberStatus.SUSPENDED;
    }

    public void activate() {
        this.status = MemberStatus.ACTIVE;
    }

    public void addPoint(Long amount) {
        this.availablePoint += amount;
    }

    public void deductPoint(Long amount) {
        if (this.availablePoint < amount) {
            throw new IllegalArgumentException("보유한 포인트보다 많은 금액을 차감할 수 없습니다.");
        }
        this.availablePoint -= amount;
    }
}
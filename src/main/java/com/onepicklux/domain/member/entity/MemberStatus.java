package com.onepicklux.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberStatus {
    ACTIVE("활성 계정"),
    DORMANT("휴면 계정"),
    WITHDRAWN("탈퇴 계정");

    private final String description;
}
package com.onepicklux.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberGrade {
    BRONZE("브론즈", 0),
    SILVER("실버", 3000000),
    GOLD("골드", 10000000),
    VIP("VIP", 50000000);

    private final String title;
    private final long minTotalSpent;
}
package com.onepicklux.domain.selling.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SellingStatus {
    REQUESTED("신청 완료"),
    REVIEWING("검수/심사 중"),
    APPROVED("승인 (매입/위탁 확정)"),
    REJECTED("반려 (매입 불가)");

    private final String description;
}
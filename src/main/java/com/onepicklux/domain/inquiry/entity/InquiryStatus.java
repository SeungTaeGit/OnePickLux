package com.onepicklux.domain.inquiry.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InquiryStatus {
    WAITING("답변 대기"),
    ANSWERED("답변 완료");

    private final String description;
}
package com.onepicklux.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointType {
    EARN("적립"),
    USE("사용"),
    GRANT("관리자 지급"),
    DEDUCT("관리자 차감");

    private final String description;
}
package com.onepicklux.domain.inquiry.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InquiryType {
    PRODUCT("상품 문의"),
    SHIPPING("배송 문의"),
    RETURN("교환/반품 문의"),
    ORDER("결제/주문 문의"),
    ETC("기타 문의");

    private final String description;
}
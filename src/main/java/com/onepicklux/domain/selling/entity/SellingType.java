package com.onepicklux.domain.selling.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SellingType {
    CONSIGNMENT("위탁 판매"),
    INSTANT("즉시 매입");

    private final String description;
}
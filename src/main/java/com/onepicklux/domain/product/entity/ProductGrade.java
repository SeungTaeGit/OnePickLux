package com.onepicklux.domain.product.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductGrade {
    NEW("새상품(미개봉)"),
    S("S급(전시상품급)"),
    A_PLUS("A+급(미세한 사용감)"),
    A("A급(자연스러운 사용감)"),
    B("B급(눈에 띄는 흠집 있음)");

    private final String description;
}
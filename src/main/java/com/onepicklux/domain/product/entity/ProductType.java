package com.onepicklux.domain.product.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {
    PRE_OWNED("중고 명품"),
    PARALLEL_IMPORT("병행수입(새상품)");

    private final String description;
}
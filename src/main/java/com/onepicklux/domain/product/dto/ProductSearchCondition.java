package com.onepicklux.domain.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductSearchCondition {
    private String keyword;
    private Long categoryId;
    private Long brandId;
    private Integer minPrice;
    private Integer maxPrice;
}
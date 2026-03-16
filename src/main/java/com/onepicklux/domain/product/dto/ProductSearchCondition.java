package com.onepicklux.domain.product.dto;

import com.onepicklux.domain.product.entity.ProductType;
import lombok.Data;

import java.util.List;

@Data
public class ProductSearchCondition {
    private String keyword;
    private Long categoryId;
    private Long brandId;
    private Integer minPrice;
    private Integer maxPrice;
    private String filter;
    private ProductType type;
}
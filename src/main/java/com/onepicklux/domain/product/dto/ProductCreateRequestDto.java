package com.onepicklux.domain.product.dto;

import com.onepicklux.domain.product.entity.ProductGrade;
import com.onepicklux.domain.product.entity.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateRequestDto {
    private Long brandId;
    private Long categoryId;
    private String name;
    private int price;
    private ProductGrade grade;
    private ProductStatus status;
    private String description;
    private Integer discountRate;
}
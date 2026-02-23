package com.onepicklux.domain.admin.dto;

import com.onepicklux.domain.product.entity.ProductGrade;
import com.onepicklux.domain.product.entity.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AdminProductUpdateRequest {

    private Long brandId;
    private Long categoryId;

    private String name;
    private Integer price;
    private Integer discountRate;
    private ProductStatus status;

    private ProductGrade grade;
    private String description;

    private String thumbnailUrl;
    private List<String> imageUrls;
}
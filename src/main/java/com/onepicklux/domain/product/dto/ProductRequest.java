package com.onepicklux.domain.product.dto;

import com.onepicklux.domain.product.entity.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductRequest {

    @NotNull(message = "브랜드 ID는 필수입니다.")
    private Long brandId;

    @NotNull(message = "카테고리 ID는 필수입니다.")
    private Long categoryId;

    @NotBlank(message = "상품명은 필수입니다.")
    private String name;

    @NotNull(message = "가격은 필수입니다.")
    private Integer price;

    @NotNull(message = "상품 등급은 필수입니다.")
    private ProductGrade grade;

    @NotNull(message = "판매 상태는 필수입니다.")
    private ProductStatus status;

    private String description;

    private String thumbnailUrl;

    private List<String> imageUrls;

    public Product toEntity(Brand brand, Category category) {
        return Product.builder()
                .brand(brand)
                .category(category)
                .name(name)
                .price(price)
                .grade(grade)
                .status(status)
                .description(description)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}
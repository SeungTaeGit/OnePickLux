package com.onepicklux.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onepicklux.domain.product.entity.Product;
import com.onepicklux.domain.product.entity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long productId;
    private String brandName;
    private String categoryName;
    private String name;
    private int price;
    private String grade;
    private String status;
    private String thumbnailUrl;
    private List<String> imageUrls;
    private int discountRate;

    @JsonProperty("isLiked")
    private boolean isLiked;

    public static ProductResponse of(Product product) {
        return of(product, false);
    }

    public static ProductResponse of(Product product, boolean isLiked) {
        return ProductResponse.builder()
                .productId(product.getId())
                .brandName(product.getBrand().getKoreanName() + " (" + product.getBrand().getEnglishName() + ")")
                .categoryName(product.getCategory().getName())
                .name(product.getName())
                .price(product.getPrice())
                .discountRate(product.getDiscountRate())
                .grade(product.getGrade().getDescription())
                .status(product.getStatus().getDescription())
                .thumbnailUrl(product.getThumbnailUrl())
                .imageUrls(
                        product.getImages() != null ?
                                product.getImages().stream()
                                        .map(ProductImage::getImageUrl)
                                        .collect(Collectors.toList())
                                : null
                )
                .isLiked(isLiked)
                .build();
    }
}
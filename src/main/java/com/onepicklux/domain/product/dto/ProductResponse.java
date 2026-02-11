package com.onepicklux.domain.product.dto;

import com.onepicklux.domain.product.entity.Product;
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

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .productId(product.getId())
                .brandName(product.getBrand().getName())
                .categoryName(product.getCategory().getName())
                .name(product.getName())
                .price(product.getPrice())
                .grade(product.getGrade().getDescription())
                .status(product.getStatus().getDescription())
                .thumbnailUrl(product.getThumbnailUrl())
                .imageUrls(product.getImages().stream()
                        .map(image -> image.getImageUrl())
                        .collect(Collectors.toList()))
                .build();
    }
}
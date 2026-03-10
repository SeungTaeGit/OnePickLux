package com.onepicklux.domain.product.dto;

import com.onepicklux.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ProductDetailResponseDto {
    private Long productId;
    private String name;
    private int price;
    private String description;

    private String thumbnailUrl;
    private List<String> detailImages;

    public static ProductDetailResponseDto from(Product product) {
        return ProductDetailResponseDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .thumbnailUrl(product.getThumbnailUrl())
                .detailImages(product.getImages().stream()
                        .map(image -> image.getImageUrl())
                        .collect(Collectors.toList()))
                .build();
    }
}
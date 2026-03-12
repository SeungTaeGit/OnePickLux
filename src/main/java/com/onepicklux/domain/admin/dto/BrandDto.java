package com.onepicklux.domain.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onepicklux.domain.product.entity.Brand;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class BrandDto {

    @Getter
    @Setter
    public static class Request {
        private String englishName;
        private String koreanName;

        @JsonProperty("isDisplay")
        private boolean isDisplay;
    }

    @Getter
    @Builder
    public static class Response {
        private Long id;
        private String englishName;
        private String koreanName;
        private String name;
        private String logoUrl;
        private boolean isDisplay;

        public static Response from(Brand brand) {
            return Response.builder()
                    .id(brand.getId())
                    .englishName(brand.getEnglishName())
                    .koreanName(brand.getKoreanName())
                    .name(brand.getKoreanName() + " (" + brand.getEnglishName() + ")")
                    .logoUrl(brand.getLogoUrl())
                    .isDisplay(brand.isDisplay())
                    .build();
        }
    }
}
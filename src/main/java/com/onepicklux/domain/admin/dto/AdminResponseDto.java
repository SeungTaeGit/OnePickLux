package com.onepicklux.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

public class AdminResponseDto {

    @Getter
    @Builder
    public static class AdminProductResponse {
        private Long productId;
        private String brandName;
        private String name;
        private Integer price;
        private Integer discountRate;
        private Integer stock;
        private String status;
    }

    @Getter
    @Builder
    public static class AdminSellingRequestResponse {
        private Long requestId;
        private String userName;
        private String requestType;
        private String brandName;
        private String itemName;
        private Integer expectedPrice;
        private String status;
        private LocalDateTime requestedAt;
    }
}
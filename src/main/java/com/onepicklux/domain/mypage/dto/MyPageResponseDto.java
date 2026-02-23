package com.onepicklux.domain.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class MyPageResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileInfo {
        private String email;
        private String name;
        private String phone;
        private LocalDateTime joinedAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderHistory {
        private Long orderId;
        private String orderNumber;
        private String productName;
        private String brandName;
        private String thumbnailUrl;
        private Integer orderPrice;
        private String orderStatus;
        private LocalDateTime orderDate;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SellingHistory {
        private Long sellingId;
        private String requestType;
        private String brandName;
        private String itemName;
        private Integer desiredPrice;
        private String sellingStatus;
        private LocalDateTime requestedAt;
    }
}
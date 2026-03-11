package com.onepicklux.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminDashboardResponse {
    private long totalMembers;
    private long totalProducts;
    private long pendingSellingRequests;
    private long todayNewOrders;
    private long todayRevenue; // 결제 붙기 전까지는 임시 처리

    private long totalOriginalInventoryValue;
    private long totalDiscountedInventoryValue;
    private long soldOutCount;
}
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
}
package com.onepicklux.domain.admin.controller;

import com.onepicklux.domain.admin.dto.AdminDashboardResponse;
import com.onepicklux.domain.admin.service.AdminService;
import com.onepicklux.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminService adminService;

    @GetMapping
    public ApiResponse<AdminDashboardResponse> getDashboardStats() {
        return ApiResponse.success(adminService.getDashboardStats());
    }
}
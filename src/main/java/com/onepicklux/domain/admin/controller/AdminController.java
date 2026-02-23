package com.onepicklux.domain.admin.controller;

import com.onepicklux.domain.admin.dto.AdminDashboardResponse;
import com.onepicklux.domain.admin.dto.AdminResponseDto;
import com.onepicklux.domain.admin.service.AdminService;
import com.onepicklux.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
// @PreAuthorize("hasRole('ADMIN')") // 💡중요: 실제 운영시엔 어드민 권한 체크 필수! (현재는 테스트를 위해 주석 처리)
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/products")
    public ApiResponse<List<AdminResponseDto.AdminProductResponse>> getAllProducts() {
        return ApiResponse.success(adminService.getAllProducts());
    }

    @GetMapping("/selling-requests")
    public ApiResponse<List<AdminResponseDto.AdminSellingRequestResponse>> getAllSellingRequests() {
        return ApiResponse.success(adminService.getAllSellingRequests());
    }
}
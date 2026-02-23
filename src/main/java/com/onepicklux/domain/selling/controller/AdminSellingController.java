package com.onepicklux.domain.selling.controller;

import com.onepicklux.domain.admin.dto.AdminSellingApproveRequest;
import com.onepicklux.domain.selling.dto.SellingResponseDto;
import com.onepicklux.domain.selling.entity.SellingStatus;
import com.onepicklux.domain.selling.service.SellingService;
import com.onepicklux.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/selling")
@RequiredArgsConstructor
public class AdminSellingController {

    private final SellingService sellingService;

    @GetMapping
    public ApiResponse<List<SellingResponseDto>> getAllRequests() {
        return ApiResponse.success(sellingService.getAllSellingRequests());
    }

    @PatchMapping("/{requestId}/status")
    public ApiResponse<String> updateStatus(
            @PathVariable Long requestId,
            @RequestParam SellingStatus status
    ) {
        sellingService.updateSellingStatus(requestId, status);
        return ApiResponse.success("매입 신청 상태가 " + status.getDescription() + "(으)로 변경되었습니다.");
    }

    @PostMapping("/{requestId}/approve")
    public ApiResponse<String> approveAndCreateProduct(
            @PathVariable Long requestId,
            @Valid @RequestBody AdminSellingApproveRequest request
    ) {
        sellingService.approveAndCreateProduct(requestId, request);
        return ApiResponse.success("승인 완료: 상품과 검수 리포트가 성공적으로 등록되었습니다.");
    }
}
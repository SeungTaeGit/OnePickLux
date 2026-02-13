package com.onepicklux.domain.selling.controller;

import com.onepicklux.domain.selling.dto.SellingResponseDto;
import com.onepicklux.domain.selling.entity.SellingStatus;
import com.onepicklux.domain.selling.service.SellingService;
import com.onepicklux.global.common.ApiResponse;
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
}
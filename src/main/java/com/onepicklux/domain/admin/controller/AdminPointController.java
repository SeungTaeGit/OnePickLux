package com.onepicklux.domain.admin.controller;

import com.onepicklux.domain.admin.dto.AdminPointDto;
import com.onepicklux.domain.admin.service.AdminPointService;
import com.onepicklux.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/points")
@RequiredArgsConstructor
public class AdminPointController {

    private final AdminPointService adminPointService;

    @GetMapping
    public ApiResponse<Page<AdminPointDto.PointLogResponse>> getPointLogs(
            @PageableDefault(size = 10) Pageable pageable) {
        return ApiResponse.success(adminPointService.getAllPointLogs(pageable));
    }
}
package com.onepicklux.domain.selling.controller;

import com.onepicklux.domain.selling.dto.SellingRequestDto;
import com.onepicklux.domain.selling.dto.SellingResponseDto;
import com.onepicklux.domain.selling.service.SellingService;
import com.onepicklux.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/selling")
@RequiredArgsConstructor
public class SellingController {

    private final SellingService sellingService;

    @PostMapping
    public ApiResponse<Long> createSellingRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody SellingRequestDto request
    ) {
        Long requestId = sellingService.createSellingRequest(userDetails.getUsername(), request);
        return ApiResponse.created(requestId);
    }

    @GetMapping("/me")
    public ApiResponse<List<SellingResponseDto>> getMySellingRequests(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ApiResponse.success(sellingService.getMySellingRequests(userDetails.getUsername()));
    }
}
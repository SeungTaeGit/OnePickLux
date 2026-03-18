package com.onepicklux.domain.inquiry.controller;

import com.onepicklux.domain.inquiry.dto.InquiryDto;
import com.onepicklux.domain.inquiry.service.InquiryService;
import com.onepicklux.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inquiries")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @PostMapping
    public ApiResponse<InquiryDto.Response> createInquiry(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody InquiryDto.Request request) {

        Long memberId = Long.parseLong(userDetails.getUsername());
        return ApiResponse.success(inquiryService.createInquiry(memberId, request));
    }

    @GetMapping("/me")
    public ApiResponse<List<InquiryDto.Response>> getMyInquiries(
            @AuthenticationPrincipal UserDetails userDetails) {

        Long memberId = Long.parseLong(userDetails.getUsername());
        return ApiResponse.success(inquiryService.getMyInquiries(memberId));
    }
}
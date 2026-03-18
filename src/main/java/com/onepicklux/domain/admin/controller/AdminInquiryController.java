package com.onepicklux.domain.admin.controller;

import com.onepicklux.domain.inquiry.dto.InquiryDto;
import com.onepicklux.domain.inquiry.service.InquiryService;
import com.onepicklux.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/inquiries")
@RequiredArgsConstructor
public class AdminInquiryController {

    private final InquiryService inquiryService;

    @GetMapping
    public ApiResponse<List<InquiryDto.Response>> getAllInquiries() {
        return ApiResponse.success(inquiryService.getAllInquiries());
    }

    @PostMapping("/{inquiryId}/answer")
    public ApiResponse<InquiryDto.Response> answerInquiry(
            @PathVariable Long inquiryId,
            @RequestBody InquiryDto.AnswerRequest request) {

        return ApiResponse.success(inquiryService.answerInquiry(inquiryId, request));
    }
}
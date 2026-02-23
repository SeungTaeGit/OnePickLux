package com.onepicklux.domain.mypage.controller;

import com.onepicklux.domain.mypage.dto.MyPageResponseDto;
import com.onepicklux.domain.mypage.service.MyPageService;
import com.onepicklux.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/profile")
    public ApiResponse<MyPageResponseDto.ProfileInfo> getProfile(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.success(myPageService.getProfile(userDetails.getUsername()));
    }

    @GetMapping("/selling")
    public ApiResponse<List<MyPageResponseDto.SellingHistory>> getSellingHistory(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.success(myPageService.getSellingHistory(userDetails.getUsername()));
    }

    @GetMapping("/orders")
    public ApiResponse<List<MyPageResponseDto.OrderHistory>> getOrderHistory(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.success(myPageService.getOrderHistory(userDetails.getUsername()));
    }
}
package com.onepicklux.domain.admin.controller;

import com.onepicklux.domain.admin.entity.Banner;
import com.onepicklux.domain.admin.repository.BannerRepository;
import com.onepicklux.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/banners")
@RequiredArgsConstructor
public class BannerController {

    private final BannerRepository bannerRepository;

    @GetMapping
    public ApiResponse<List<Banner>> getActiveBanners() {
        List<Banner> activeBanners = bannerRepository.findByIsActiveTrueOrderByCreatedAtDesc();
        return ApiResponse.success(activeBanners);
    }
}
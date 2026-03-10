package com.onepicklux.domain.admin.controller;

import com.onepicklux.domain.admin.entity.Banner;
import com.onepicklux.domain.admin.repository.BannerRepository;
import com.onepicklux.global.common.ApiResponse;
import com.onepicklux.global.common.S3UploaderService; // 💡 FileService 대신 S3UploaderService 임포트
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/banners")
@RequiredArgsConstructor
public class AdminBannerController {

    private final BannerRepository bannerRepository;
    private final S3UploaderService s3UploaderService;

    @GetMapping
    public ApiResponse<List<Banner>> getBanners() {
        return ApiResponse.success(bannerRepository.findAllByOrderByCreatedAtDesc());
    }

    @PostMapping
    public ApiResponse<String> createBanner(
            @RequestParam("title") String title,
            @RequestParam(value = "linkUrl", required = false) String linkUrl,
            @RequestParam("isActive") boolean isActive,
            @RequestParam("image") MultipartFile image
    ) {
        String imageUrl = s3UploaderService.uploadImage(image);

        if (imageUrl == null) {
            throw new IllegalArgumentException("이미지 파일은 필수입니다.");
        }

        Banner banner = Banner.builder()
                .title(title)
                .linkUrl(linkUrl)
                .isActive(isActive)
                .imageUrl(imageUrl)
                .build();

        bannerRepository.save(banner);
        return ApiResponse.success("배너가 성공적으로 등록되었습니다.");
    }

    @PatchMapping("/{bannerId}/status")
    public ApiResponse<String> toggleBannerStatus(@PathVariable Long bannerId) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new IllegalArgumentException("배너를 찾을 수 없습니다."));

        banner.updateStatus(!banner.isActive());
        bannerRepository.save(banner);
        return ApiResponse.success("배너 상태가 변경되었습니다.");
    }

    @DeleteMapping("/{bannerId}")
    public ApiResponse<String> deleteBanner(@PathVariable Long bannerId) {
        bannerRepository.deleteById(bannerId);
        return ApiResponse.success("배너가 삭제되었습니다.");
    }
}
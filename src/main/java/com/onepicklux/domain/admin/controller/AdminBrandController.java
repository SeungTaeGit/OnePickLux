package com.onepicklux.domain.admin.controller;

import com.onepicklux.domain.admin.dto.BrandDto;
import com.onepicklux.domain.admin.service.AdminBrandService;
import com.onepicklux.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/brands")
@RequiredArgsConstructor
public class AdminBrandController {

    private final AdminBrandService adminBrandService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BrandDto.Response>>> getAllBrands() {
        return ResponseEntity.ok(ApiResponse.success(adminBrandService.getAllBrands()));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<BrandDto.Response>>> getActiveBrands() {
        return ResponseEntity.ok(ApiResponse.success(adminBrandService.getActiveBrands()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BrandDto.Response>> createBrand(
            @RequestPart(value = "request") BrandDto.Request request,
            @RequestPart(value = "logoImage", required = false) MultipartFile logoImage,
            @RequestPart(value = "bannerImage", required = false) MultipartFile bannerImage) {

        BrandDto.Response response = adminBrandService.createBrand(request, logoImage, bannerImage);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{brandId}")
    public ResponseEntity<ApiResponse<BrandDto.Response>> updateBrand(
            @PathVariable Long brandId,
            @RequestPart(value = "request") BrandDto.Request request,
            @RequestPart(value = "logoImage", required = false) MultipartFile logoImage,
            @RequestPart(value = "bannerImage", required = false) MultipartFile bannerImage) {

        BrandDto.Response response = adminBrandService.updateBrand(brandId, request, logoImage, bannerImage);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable Long brandId) {
        adminBrandService.deleteBrand(brandId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
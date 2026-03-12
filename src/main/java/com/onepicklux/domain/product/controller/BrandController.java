package com.onepicklux.domain.product.controller;

import com.onepicklux.domain.admin.dto.BrandDto;
import com.onepicklux.domain.product.service.BrandService;
import com.onepicklux.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<BrandDto.Response>>> getActiveBrands() {
        return ResponseEntity.ok(ApiResponse.success(brandService.getActiveBrands()));
    }
}
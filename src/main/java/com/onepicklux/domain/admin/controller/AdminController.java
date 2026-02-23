package com.onepicklux.domain.admin.controller;

import com.onepicklux.domain.admin.dto.AdminDashboardResponse;
import com.onepicklux.domain.admin.dto.AdminProductUpdateRequest;
import com.onepicklux.domain.admin.dto.AdminResponseDto;
import com.onepicklux.domain.admin.service.AdminService;
import com.onepicklux.domain.product.dto.ProductRequest;
import com.onepicklux.domain.product.dto.ProductResponse;
import com.onepicklux.domain.product.service.ProductService;
import com.onepicklux.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final ProductService productService;

    @GetMapping("/dashboard")
    public ApiResponse<AdminDashboardResponse> getDashboardStats() {
        return ApiResponse.success(adminService.getDashboardStats());
    }

    @GetMapping("/products")
    public ApiResponse<List<AdminResponseDto.AdminProductResponse>> getAllProducts() {
        return ApiResponse.success(adminService.getAllProducts());
    }

    @GetMapping("/selling-requests")
    public ApiResponse<List<AdminResponseDto.AdminSellingRequestResponse>> getAllSellingRequests() {
        return ApiResponse.success(adminService.getAllSellingRequests());
    }

    @PostMapping("/products")
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        return ApiResponse.created(productService.createProduct(request));
    }

    @PatchMapping("/products/{productId}")
    public ApiResponse<String> updateProduct(
            @PathVariable Long productId,
            @RequestBody AdminProductUpdateRequest request
    ) {
        adminService.updateProduct(productId, request);
        return ApiResponse.success("상품 정보가 성공적으로 수정되었습니다.");
    }

    @DeleteMapping("/products/{productId}")
    public ApiResponse<String> deleteProduct(@PathVariable Long productId) {
        adminService.deleteProduct(productId);
        return ApiResponse.success("상품이 안전하게 삭제(숨김) 처리되었습니다.");
    }
}
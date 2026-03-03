package com.onepicklux.domain.admin.controller;

import com.onepicklux.domain.admin.dto.AdminDashboardResponse;
import com.onepicklux.domain.admin.dto.AdminProductUpdateRequest;
import com.onepicklux.domain.admin.dto.AdminResponseDto;
import com.onepicklux.domain.admin.service.AdminService;
import com.onepicklux.domain.product.dto.ProductRequest;
import com.onepicklux.domain.product.dto.ProductResponse;
import com.onepicklux.domain.product.service.ProductService;
import com.onepicklux.global.common.ApiResponse;
import com.onepicklux.global.common.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final ProductService productService;
    private final FileService fileService;

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

    @PostMapping("/products/new")
    public ApiResponse<String> createDirectProduct(
            @RequestParam("brandId") Long brandId,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("name") String name,
            @RequestParam("price") Integer price,
            @RequestParam("discountRate") Integer discountRate,
            @RequestParam("status") String status,
            @RequestParam("grade") String grade,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        String thumbnailUrl = null;
        if (image != null && !image.isEmpty()) {
            thumbnailUrl = fileService.uploadImage(image);
        }

        adminService.registerDirectProduct(
                brandId, categoryId, name, price, discountRate, status, grade, description, thumbnailUrl
        );

        return ApiResponse.success("상품이 성공적으로 직접 등록되었습니다.");
    }
}
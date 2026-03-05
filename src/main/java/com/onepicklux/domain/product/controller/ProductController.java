package com.onepicklux.domain.product.controller;

import com.onepicklux.domain.product.dto.ProductResponse;
import com.onepicklux.domain.product.dto.ProductSearchCondition;
import com.onepicklux.domain.product.service.ProductService;
import com.onepicklux.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ApiResponse<Page<ProductResponse>> getProducts(
            ProductSearchCondition condition,
            @RequestParam(required = false, defaultValue = "new") String sort,
            @PageableDefault(size = 20) Pageable pageable,
            @AuthenticationPrincipal UserDetails userDetails) {

        Sort customSort = Sort.by(Sort.Direction.DESC, "createdAt");

        if ("best".equalsIgnoreCase(sort)) {
            customSort = Sort.by(Sort.Direction.DESC, "viewCount");
        } else if ("sale".equalsIgnoreCase(sort)) {
            customSort = Sort.by(Sort.Direction.DESC, "discountRate");
        }

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), customSort);

        Long memberId = (userDetails != null) ? Long.parseLong(userDetails.getUsername()) : null;

        return ApiResponse.success(productService.getProducts(condition, pageRequest, memberId));
    }

    @GetMapping("/{productId}")
    public ApiResponse<ProductResponse> getProduct(
            @PathVariable Long productId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long memberId = (userDetails != null) ? Long.parseLong(userDetails.getUsername()) : null;

        return ApiResponse.success(productService.getProduct(productId, memberId));
    }
}
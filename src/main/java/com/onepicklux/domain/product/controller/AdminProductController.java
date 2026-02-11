package com.onepicklux.domain.product.controller;

import com.onepicklux.domain.product.dto.ProductRequest;
import com.onepicklux.domain.product.dto.ProductResponse;
import com.onepicklux.domain.product.service.ProductService;
import com.onepicklux.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        return ApiResponse.created(productService.createProduct(request));
    }
}
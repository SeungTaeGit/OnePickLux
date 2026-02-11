package com.onepicklux.domain.product.controller;

import com.onepicklux.domain.product.service.ProductLikeService;
import com.onepicklux.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductLikeController {

    private final ProductLikeService productLikeService;

    @PostMapping("/{productId}/likes")
    public ApiResponse<String> toggleLike(
            @PathVariable Long productId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        boolean result = productLikeService.toggleLike(productId, userDetails.getUsername());

        if (result) {
            return ApiResponse.success("상품을 찜했습니다.");
        } else {
            return ApiResponse.success("찜하기를 취소했습니다.");
        }
    }
}
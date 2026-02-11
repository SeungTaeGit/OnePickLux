package com.onepicklux.domain.cart.controller;

import com.onepicklux.domain.cart.dto.AddCartRequest;
import com.onepicklux.domain.cart.dto.CartItemResponse;
import com.onepicklux.domain.cart.service.CartService;
import com.onepicklux.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ApiResponse<Long> addCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody AddCartRequest request
    ) {
        Long cartItemId = cartService.addCart(userDetails.getUsername(), request);
        return ApiResponse.success(cartItemId);
    }

    @GetMapping
    public ApiResponse<List<CartItemResponse>> getCartList(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ApiResponse.success(cartService.getCartList(userDetails.getUsername()));
    }

    @DeleteMapping("/{cartItemId}")
    public ApiResponse<String> deleteCartItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long cartItemId
    ) {
        cartService.deleteCartItem(userDetails.getUsername(), cartItemId);
        return ApiResponse.success("장바구니에서 상품을 삭제했습니다.");
    }
}
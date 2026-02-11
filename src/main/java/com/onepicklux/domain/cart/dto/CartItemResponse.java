package com.onepicklux.domain.cart.dto;

import com.onepicklux.domain.cart.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Long cartItemId;
    private Long productId;
    private String brandName;
    private String productName;
    private int price;
    private int count;
    private String thumbnailUrl;

    public static CartItemResponse of(CartItem cartItem) {
        return CartItemResponse.builder()
                .cartItemId(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .brandName(cartItem.getProduct().getBrand().getName())
                .productName(cartItem.getProduct().getName())
                .price(cartItem.getProduct().getPrice())
                .count(cartItem.getCount())
                .thumbnailUrl(cartItem.getProduct().getThumbnailUrl())
                .build();
    }
}
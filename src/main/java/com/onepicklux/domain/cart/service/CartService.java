package com.onepicklux.domain.cart.service;

import com.onepicklux.domain.cart.dto.AddCartRequest;
import com.onepicklux.domain.cart.dto.CartItemResponse;
import com.onepicklux.domain.cart.entity.Cart;
import com.onepicklux.domain.cart.entity.CartItem;
import com.onepicklux.domain.cart.repository.CartItemRepository;
import com.onepicklux.domain.cart.repository.CartRepository;
import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.domain.member.repository.MemberRepository;
import com.onepicklux.domain.product.entity.Product;
import com.onepicklux.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Long addCart(String memberIdString, AddCartRequest request) {
        Long memberId = Long.parseLong(memberIdString);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));

        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseGet(() -> {
                    Cart newCart = Cart.createCart(member);
                    return cartRepository.save(newCart);
                });

        boolean isAlreadyExist = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId()).isPresent();

        if (isAlreadyExist) {
            throw new IllegalArgumentException("이미 장바구니에 담긴 상품입니다.");
        }

        CartItem cartItem = CartItem.createCartItem(cart, product, request.getCount());
        cartItemRepository.save(cartItem);
        return cartItem.getId();
    }

    public List<CartItemResponse> getCartList(String memberIdString) {
        Long memberId = Long.parseLong(memberIdString);

        Cart cart = cartRepository.findByMemberId(memberId).orElse(null);

        if (cart == null) {
            return List.of();
        }

        return cartItemRepository.findByCartId(cart.getId()).stream()
                .map(CartItemResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCartItem(String memberIdString, Long cartItemId) {
        Long memberId = Long.parseLong(memberIdString);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 아이템을 찾을 수 없습니다."));

        if (!cartItem.getCart().getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("해당 장바구니 항목에 대한 권한이 없습니다.");
        }

        cartItemRepository.delete(cartItem);
    }

    public int getCartItemCount(String memberIdString) {
        Long memberId = Long.parseLong(memberIdString);

        return cartRepository.findByMemberId(memberId)
                .map(cart -> cartItemRepository.countByCartId(cart.getId()))
                .orElse(0);
    }
}
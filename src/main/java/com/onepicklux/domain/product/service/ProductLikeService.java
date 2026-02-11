package com.onepicklux.domain.product.service;

import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.domain.member.repository.MemberRepository;
import com.onepicklux.domain.product.entity.Product;
import com.onepicklux.domain.product.entity.ProductLike;
import com.onepicklux.domain.product.repository.ProductLikeRepository;
import com.onepicklux.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductLikeService {

    private final ProductLikeRepository productLikeRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public boolean toggleLike(Long productId, String memberIdString) {
        Long memberId = Long.parseLong(memberIdString);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));

        return productLikeRepository.findByMemberAndProduct(member, product)
                .map(productLike -> {
                    productLikeRepository.delete(productLike);
                    return false;
                })
                .orElseGet(() -> {
                    ProductLike newLike = ProductLike.builder()
                            .member(member)
                            .product(product)
                            .build();
                    productLikeRepository.save(newLike);
                    return true;
                });
    }
}
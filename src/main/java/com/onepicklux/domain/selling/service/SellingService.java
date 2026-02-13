package com.onepicklux.domain.selling.service;

import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.domain.member.repository.MemberRepository;
import com.onepicklux.domain.product.entity.*;
import com.onepicklux.domain.product.repository.BrandRepository;
import com.onepicklux.domain.product.repository.CategoryRepository;
import com.onepicklux.domain.product.repository.ProductRepository;
import com.onepicklux.domain.selling.dto.SellingRequestDto;
import com.onepicklux.domain.selling.dto.SellingResponseDto;
import com.onepicklux.domain.selling.entity.SellingRequest;
import com.onepicklux.domain.selling.entity.SellingStatus;
import com.onepicklux.domain.selling.repository.SellingRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellingService {

    private final SellingRequestRepository sellingRequestRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long createSellingRequest(String memberIdString, SellingRequestDto requestDto) {
        Long memberId = Long.parseLong(memberIdString);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        SellingRequest sellingRequest = requestDto.toEntity(member);
        sellingRequestRepository.save(sellingRequest);

        return sellingRequest.getId();
    }

    public List<SellingResponseDto> getMySellingRequests(String memberIdString) {
        Long memberId = Long.parseLong(memberIdString);

        return sellingRequestRepository.findByMemberIdOrderByCreatedAtDesc(memberId).stream()
                .map(SellingResponseDto::of)
                .collect(Collectors.toList());
    }

    public List<SellingResponseDto> getAllSellingRequests() {
        return sellingRequestRepository.findAll().stream()
                .map(SellingResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateSellingStatus(Long requestId, SellingStatus status) {
        SellingRequest sellingRequest = sellingRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매입 신청을 찾을 수 없습니다."));

        sellingRequest.changeStatus(status);

        if (status == SellingStatus.APPROVED) {
            createProductFromRequest(sellingRequest);
        }
    }

    private void createProductFromRequest(SellingRequest request) {
        Brand brand = brandRepository.findAll().stream()
                .filter(b -> b.getName().equalsIgnoreCase(request.getBrandName()))
                .findFirst()
                .orElseGet(() -> brandRepository.findAll().get(0));

        Category category = categoryRepository.findAll().get(0);

        Product product = Product.builder()
                .seller(request.getMember())
                .brand(brand)
                .category(category)
                .name(request.getItemName())
                .price(0)
                .grade(ProductGrade.S)
                .status(ProductStatus.PREPARING)
                .description("매입 신청된 상품입니다. 검수 후 판매 예정입니다.")
                .thumbnailUrl(request.getImageUrl())
                .build();

        productRepository.save(product);
    }
}
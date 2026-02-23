package com.onepicklux.domain.selling.service;

import com.onepicklux.domain.admin.dto.AdminSellingApproveRequest;
import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.domain.member.repository.MemberRepository;
import com.onepicklux.domain.product.entity.*;
import com.onepicklux.domain.product.repository.BrandRepository;
import com.onepicklux.domain.product.repository.CategoryRepository;
import com.onepicklux.domain.product.repository.InspectionRepository;
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
    private final InspectionRepository inspectionRepository;

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
        return sellingRequestRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(SellingResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateSellingStatus(Long requestId, SellingStatus status) {
        SellingRequest sellingRequest = sellingRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매입 신청을 찾을 수 없습니다."));

        sellingRequest.changeStatus(status);
    }

    @Transactional
    public void approveAndCreateProduct(Long requestId, AdminSellingApproveRequest request) {
        SellingRequest sellingRequest = sellingRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("해당 매입 신청을 찾을 수 없습니다."));

        if (sellingRequest.getStatus() == SellingStatus.APPROVED) {
            throw new IllegalArgumentException("이미 승인 처리된 신청입니다.");
        }

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 브랜드입니다."));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        Product product = Product.builder()
                .seller(sellingRequest.getMember())
                .brand(brand)
                .category(category)
                .name(request.getName())
                .price(request.getPrice())
                .grade(request.getGrade())
                .status(ProductStatus.SELLING)
                .description(request.getDescription())
                .thumbnailUrl(sellingRequest.getImageUrl())
                .build();

        productRepository.save(product);

        Inspection inspection = Inspection.builder()
                .product(product)
                .inspectorName(request.getInspectorName())
                .leatherStatus(request.getLeatherStatus())
                .hardwareStatus(request.getHardwareStatus())
                .shapeStatus(request.getShapeStatus())
                .innerStatus(request.getInnerStatus())
                .finalComment(request.getFinalComment())
                .build();

        inspectionRepository.save(inspection);

        sellingRequest.changeStatus(SellingStatus.APPROVED);
    }
}
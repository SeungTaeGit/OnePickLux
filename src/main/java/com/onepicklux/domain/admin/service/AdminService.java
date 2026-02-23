package com.onepicklux.domain.admin.service;

import com.onepicklux.domain.admin.dto.AdminDashboardResponse;
import com.onepicklux.domain.admin.dto.AdminResponseDto;
import com.onepicklux.domain.member.repository.MemberRepository;
import com.onepicklux.domain.product.repository.ProductRepository;
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
public class AdminService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final SellingRequestRepository sellingRequestRepository;

    public AdminDashboardResponse getDashboardStats() {
        return AdminDashboardResponse.builder()
                .totalMembers(memberRepository.count())
                .totalProducts(productRepository.count())
                .pendingSellingRequests(sellingRequestRepository.countByStatus(SellingStatus.REQUESTED))
                .todayNewOrders(0)
                .build();
    }

    public List<AdminResponseDto.AdminProductResponse> getAllProducts() {
        return productRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(product -> AdminResponseDto.AdminProductResponse.builder()
                        .productId(product.getId())
                        .brandName(product.getBrand().getName())
                        .name(product.getName())
                        .price(product.getPrice())
                        // .discountRate(product.getDiscountRate())
                        // .stock(product.getStock())
                        .status(product.getStatus().name())
                        .build())
                .collect(Collectors.toList());
    }

    public List<AdminResponseDto.AdminSellingRequestResponse> getAllSellingRequests() {
        return sellingRequestRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(request -> AdminResponseDto.AdminSellingRequestResponse.builder()
                        .requestId(request.getId())
                        .userName(request.getMember().getName())
                        .requestType(request.getRequestType().getDescription())
                        .brandName(request.getBrandName())
                        .itemName(request.getItemName())
                        .expectedPrice(request.getPurchasePrice())
                        .status(request.getStatus().getDescription())
                        .requestedAt(request.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
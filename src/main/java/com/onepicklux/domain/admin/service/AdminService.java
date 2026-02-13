package com.onepicklux.domain.admin.service;

import com.onepicklux.domain.admin.dto.AdminDashboardResponse;
import com.onepicklux.domain.member.repository.MemberRepository;
import com.onepicklux.domain.product.repository.ProductRepository;
import com.onepicklux.domain.selling.entity.SellingStatus;
import com.onepicklux.domain.selling.repository.SellingRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
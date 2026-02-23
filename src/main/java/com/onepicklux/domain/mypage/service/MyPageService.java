package com.onepicklux.domain.mypage.service;

import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.domain.member.repository.MemberRepository;
import com.onepicklux.domain.mypage.dto.MyPageResponseDto;
import com.onepicklux.domain.selling.repository.SellingRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final MemberRepository memberRepository;
    private final SellingRequestRepository sellingRequestRepository;
    // private final OrderRepository orderRepository;

    public MyPageResponseDto.ProfileInfo getProfile(String memberIdString) {
        Long memberId = Long.parseLong(memberIdString);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        return MyPageResponseDto.ProfileInfo.builder()
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .joinedAt(member.getCreatedAt())
                .build();
    }

    public List<MyPageResponseDto.SellingHistory> getSellingHistory(String memberIdString) {
        Long memberId = Long.parseLong(memberIdString);

        return sellingRequestRepository.findByMemberIdOrderByCreatedAtDesc(memberId).stream()
                .map(request -> MyPageResponseDto.SellingHistory.builder()
                        .sellingId(request.getId())
                        .requestType(request.getRequestType().getDescription())
                        .brandName(request.getBrandName())
                        .itemName(request.getItemName())
                        .desiredPrice(request.getPurchasePrice())
                        .sellingStatus(request.getStatus().getDescription())
                        .requestedAt(request.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public List<MyPageResponseDto.OrderHistory> getOrderHistory(String memberIdString) {
        Long memberId = Long.parseLong(memberIdString);

        return List.of();
    }
}
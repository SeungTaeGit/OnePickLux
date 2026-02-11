package com.onepicklux.domain.selling.service;

import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.domain.member.repository.MemberRepository;
import com.onepicklux.domain.selling.dto.SellingRequestDto;
import com.onepicklux.domain.selling.dto.SellingResponseDto;
import com.onepicklux.domain.selling.entity.SellingRequest;
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
}
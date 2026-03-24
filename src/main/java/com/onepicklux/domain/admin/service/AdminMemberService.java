package com.onepicklux.domain.admin.service;

import com.onepicklux.domain.admin.dto.AdminMemberDto;
import com.onepicklux.domain.admin.dto.AdminResponseDto;
import com.onepicklux.domain.admin.dto.PointDto;
import com.onepicklux.domain.inquiry.dto.InquiryDto;
import com.onepicklux.domain.inquiry.repository.InquiryRepository;
import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.domain.member.entity.MemberStatus;
import com.onepicklux.domain.member.entity.PointHistory;
import com.onepicklux.domain.member.entity.PointType;
import com.onepicklux.domain.member.repository.MemberRepository;
import com.onepicklux.domain.member.repository.PointHistoryRepository;
import com.onepicklux.domain.selling.repository.SellingRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminMemberService {

    private final MemberRepository memberRepository;
    private final SellingRequestRepository sellingRequestRepository;
    private final InquiryRepository inquiryRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public List<AdminMemberDto.MemberListResponse> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(AdminMemberDto.MemberListResponse::from)
                .collect(Collectors.toList());
    }

    public AdminMemberDto.Member360Response getMember360View(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        List<AdminResponseDto.AdminSellingRequestResponse> sellings = sellingRequestRepository.findAllByOrderByCreatedAtDesc().stream()
                .filter(req -> req.getMember().getId().equals(memberId))
                .map(request -> AdminResponseDto.AdminSellingRequestResponse.builder()
                        .requestId(request.getId())
                        .brandName(request.getBrandName())
                        .itemName(request.getItemName())
                        .status(request.getStatus().getDescription())
                        .requestedAt(request.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        List<InquiryDto.Response> inquiries = inquiryRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId).stream()
                .map(InquiryDto.Response::from)
                .collect(Collectors.toList());

        List<AdminMemberDto.PointHistoryResponse> pointHistories = pointHistoryRepository.findByMemberIdOrderByCreatedAtDesc(memberId).stream()
                .limit(10)
                .map(AdminMemberDto.PointHistoryResponse::from)
                .collect(Collectors.toList());

        return AdminMemberDto.Member360Response.builder()
                .basicInfo(AdminMemberDto.MemberListResponse.from(member))
                .adminMemo(member.getAdminMemo())
                .sellingHistory(sellings)
                .inquiryHistory(inquiries)
                .pointHistory(pointHistories)
                .build();
    }

    @Transactional
    public void updateAdminMemo(Long memberId, AdminMemberDto.UpdateMemoRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        member.updateAdminMemo(request.getMemo());
    }

    @Transactional
    public String toggleMemberSuspend(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        if (member.getStatus() == MemberStatus.SUSPENDED) {
            member.activate();
            return "회원 정지가 해제되었습니다.";
        } else {
            member.suspend();
            return "회원이 정지 처리되었습니다. (로그인 불가)";
        }
    }

    @Transactional
    public void processAdminPoint(Long memberId, PointDto.Request request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        PointType type = PointType.valueOf(request.getType());

        if (type == PointType.GRANT) {
            member.addPoint(request.getAmount());
        } else if (type == PointType.DEDUCT) {
            member.deductPoint(request.getAmount());
        } else {
            throw new IllegalArgumentException("잘못된 포인트 타입입니다.");
        }

        PointHistory history = PointHistory.builder()
                .member(member)
                .amount(request.getAmount())
                .type(type)
                .description(request.getDescription())
                .build();

        pointHistoryRepository.save(history);
    }

    public List<AdminMemberDto.PointHistoryResponse> getMemberPointHistory(Long memberId) {
        return pointHistoryRepository.findByMemberIdOrderByCreatedAtDesc(memberId).stream()
                .map(AdminMemberDto.PointHistoryResponse::from)
                .collect(Collectors.toList());
    }
}
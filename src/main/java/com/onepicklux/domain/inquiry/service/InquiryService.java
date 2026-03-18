package com.onepicklux.domain.inquiry.service;

import com.onepicklux.domain.inquiry.dto.InquiryDto;
import com.onepicklux.domain.inquiry.entity.Inquiry;
import com.onepicklux.domain.inquiry.repository.InquiryRepository;
import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public InquiryDto.Response createInquiry(Long memberId, InquiryDto.Request request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        Inquiry inquiry = Inquiry.builder()
                .member(member)
                .type(request.getType())
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        Inquiry savedInquiry = inquiryRepository.save(inquiry);
        return InquiryDto.Response.from(savedInquiry);
    }

    public List<InquiryDto.Response> getMyInquiries(Long memberId) {
        return inquiryRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId).stream()
                .map(InquiryDto.Response::from)
                .collect(Collectors.toList());
    }

    public List<InquiryDto.Response> getAllInquiries() {
        return inquiryRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(InquiryDto.Response::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public InquiryDto.Response answerInquiry(Long inquiryId, InquiryDto.AnswerRequest request) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("문의를 찾을 수 없습니다."));

        inquiry.answer(request.getAnswerContent());

        sendNotificationEmailAsync(inquiry.getMember().getEmail(), inquiry.getTitle());

        return InquiryDto.Response.from(inquiry);
    }

    @Async
    public void sendNotificationEmailAsync(String userEmail, String inquiryTitle) {
        try {
            log.info("📧 [비동기 작업 시작] {} 님에게 알림 이메일 발송 중... (대상 문의: {})", userEmail, inquiryTitle);
            // 실제 이메일 발송 로직이 들어가는 곳
            Thread.sleep(2000);
            log.info("✅ [비동기 작업 완료] 이메일 발송 성공!");
        } catch (InterruptedException e) {
            log.error("이메일 발송 중 오류 발생", e);
        }
    }
}
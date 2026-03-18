package com.onepicklux.domain.inquiry.entity;

import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inquiry")
public class Inquiry extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InquiryType type;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InquiryStatus status;

    @Column(columnDefinition = "TEXT")
    private String answerContent;

    private LocalDateTime answeredAt;

    @Builder
    public Inquiry(Member member, InquiryType type, String title, String content) {
        this.member = member;
        this.type = type;
        this.title = title;
        this.content = content;
        this.status = InquiryStatus.WAITING;
    }

    public void answer(String answerContent) {
        this.answerContent = answerContent;
        this.status = InquiryStatus.ANSWERED;
        this.answeredAt = LocalDateTime.now();
    }
}
package com.onepicklux.domain.inquiry.dto;

import com.onepicklux.domain.inquiry.entity.Inquiry;
import com.onepicklux.domain.inquiry.entity.InquiryType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class InquiryDto {

    @Getter
    @NoArgsConstructor
    public static class Request {
        private InquiryType type;
        private String title;
        private String content;
    }

    @Getter
    @NoArgsConstructor
    public static class AnswerRequest {
        private String answerContent;
    }

    @Getter
    @Builder
    public static class Response {
        private Long inquiryId;
        private String type;
        private String typeDescription;
        private String title;
        private String content;
        private String status;
        private String statusDescription;
        private String answerContent;
        private LocalDateTime createdAt;
        private LocalDateTime answeredAt;
        private String memberName;

        public static Response from(Inquiry inquiry) {
            return Response.builder()
                    .inquiryId(inquiry.getId())
                    .type(inquiry.getType().name())
                    .typeDescription(inquiry.getType().getDescription())
                    .title(inquiry.getTitle())
                    .content(inquiry.getContent())
                    .status(inquiry.getStatus().name())
                    .statusDescription(inquiry.getStatus().getDescription())
                    .answerContent(inquiry.getAnswerContent())
                    .createdAt(inquiry.getCreatedAt())
                    .answeredAt(inquiry.getAnsweredAt())
                    .memberName(inquiry.getMember().getName())
                    .build();
        }
    }
}
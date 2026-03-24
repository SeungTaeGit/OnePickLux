package com.onepicklux.domain.admin.dto;

import com.onepicklux.domain.inquiry.dto.InquiryDto;
import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.domain.member.entity.PointHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

public class AdminMemberDto {

    @Getter
    @Builder
    public static class MemberListResponse {
        private Long memberId;
        private String email;
        private String name;

        private String gender;
        private LocalDate birthDate;
        private Integer age;

        private String grade;
        private Long totalSpent;
        private Long availablePoint;
        private String status;
        private String statusDescription;
        private LocalDateTime joinedAt;
        private LocalDateTime lastLoginAt;

        public static MemberListResponse from(Member member) {
            Integer calculatedAge = null;
            if (member.getBirthDate() != null) {
                calculatedAge = Period.between(member.getBirthDate(), LocalDate.now()).getYears();
            }

            return MemberListResponse.builder()
                    .memberId(member.getId())
                    .email(member.getEmail())
                    .name(member.getName())
                    .gender(member.getGender() != null ? member.getGender().getDescription() : "미상")
                    .birthDate(member.getBirthDate())
                    .age(calculatedAge)
                    .grade(member.getGrade().getTitle())
                    .totalSpent(member.getTotalSpent())
                    .availablePoint(member.getAvailablePoint())
                    .status(member.getStatus().name())
                    .statusDescription(member.getStatus().getDescription())
                    .joinedAt(member.getCreatedAt())
                    .lastLoginAt(member.getLastLoginAt())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class PointHistoryResponse {
        private Long amount;
        private String type;
        private String typeDescription;
        private String description;
        private LocalDateTime createdAt;

        public static PointHistoryResponse from(PointHistory history) {
            return PointHistoryResponse.builder()
                    .amount(history.getAmount())
                    .type(history.getType().name())
                    .typeDescription(history.getType().getDescription())
                    .description(history.getDescription())
                    .createdAt(history.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Member360Response {
        private MemberListResponse basicInfo;
        private String adminMemo;
        private List<AdminResponseDto.AdminSellingRequestResponse> sellingHistory;
        private List<InquiryDto.Response> inquiryHistory;
        private List<PointHistoryResponse> pointHistory;
    }

    @Getter
    public static class UpdateMemoRequest {
        private String memo;
    }
}
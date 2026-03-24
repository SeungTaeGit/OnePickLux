package com.onepicklux.domain.admin.dto;

import com.onepicklux.domain.member.entity.PointHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class AdminPointDto {

    @Getter
    @Builder
    public static class PointLogResponse {
        private Long historyId;
        private Long memberId;
        private String memberName;
        private String memberEmail;
        private Long amount;
        private String type;
        private String typeDescription;
        private String description;
        private LocalDateTime createdAt;

        public static PointLogResponse from(PointHistory history) {
            return PointLogResponse.builder()
                    .historyId(history.getId())
                    .memberId(history.getMember().getId())
                    .memberName(history.getMember().getName())
                    .memberEmail(history.getMember().getEmail())
                    .amount(history.getAmount())
                    .type(history.getType().name())
                    .typeDescription(history.getType().getDescription())
                    .description(history.getDescription())
                    .createdAt(history.getCreatedAt())
                    .build();
        }
    }
}
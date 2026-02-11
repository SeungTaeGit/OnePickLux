package com.onepicklux.domain.selling.dto;

import com.onepicklux.domain.selling.entity.SellingRequest;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class SellingResponseDto {
    private Long requestId;
    private String requestType;
    private String brandName;
    private String itemName;
    private String status;
    private String requestedAt;

    public static SellingResponseDto of(SellingRequest request) {
        return SellingResponseDto.builder()
                .requestId(request.getId())
                .requestType(request.getRequestType().getDescription())
                .brandName(request.getBrandName())
                .itemName(request.getItemName())
                .status(request.getStatus().getDescription())
                .requestedAt(request.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
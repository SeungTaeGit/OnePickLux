package com.onepicklux.domain.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class PointDto {

    @Getter
    @NoArgsConstructor
    public static class Request {
        private Long amount;
        private String type;
        private String description;
    }
}
package com.onepicklux.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @Column(nullable = false)
    private String englishName;

    @Column(nullable = false)
    private String koreanName;

    private String logoUrl;

    private boolean isDisplay;

    @Builder
    public Brand(String englishName, String koreanName, String logoUrl, boolean isDisplay) {
        this.englishName = englishName;
        this.koreanName = koreanName;
        this.logoUrl = logoUrl;
        this.isDisplay = isDisplay;
    }
}
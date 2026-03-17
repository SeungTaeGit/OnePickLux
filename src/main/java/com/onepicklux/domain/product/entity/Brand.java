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

    @Column(nullable = false, unique = true)
    private String englishName;

    @Column(nullable = false, unique = true)
    private String koreanName;

    private String logoUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 10)
    private String themeColor;

    private String bannerUrl;

    private boolean isDisplay;

    @Builder
    public Brand(String englishName, String koreanName, String logoUrl,
                 String description, String themeColor, String bannerUrl, boolean isDisplay) {
        this.englishName = englishName;
        this.koreanName = koreanName;
        this.logoUrl = logoUrl;
        this.description = description;
        this.themeColor = (themeColor != null) ? themeColor : "#1A1A1A";
        this.bannerUrl = bannerUrl;
        this.isDisplay = isDisplay;
    }

    public void updateInfo(String englishName, String koreanName, String logoUrl,
                           String description, String themeColor, String bannerUrl, boolean isDisplay) {
        this.englishName = englishName;
        this.koreanName = koreanName;
        if (logoUrl != null) this.logoUrl = logoUrl;

        this.description = description;
        if (themeColor != null) this.themeColor = themeColor;
        if (bannerUrl != null) this.bannerUrl = bannerUrl;

        this.isDisplay = isDisplay;
    }
}
package com.onepicklux.domain.selling.entity;

import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "selling_request")
public class SellingRequest extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SellingType requestType;

    @Column(nullable = false)
    private String brandName;

    @Column(nullable = false)
    private String itemName;

    private String purchaseYear;

    private Integer purchasePrice;

    @Enumerated(EnumType.STRING)
    private SellingStatus status;

    private String imageUrl;

    @Builder
    public SellingRequest(Member member, SellingType requestType, String brandName, String itemName, String purchaseYear, Integer purchasePrice, String imageUrl) {
        this.member = member;
        this.requestType = requestType;
        this.brandName = brandName;
        this.itemName = itemName;
        this.purchaseYear = purchaseYear;
        this.purchasePrice = purchasePrice;
        this.imageUrl = imageUrl;
        this.status = SellingStatus.REQUESTED;
    }

    public void changeStatus(SellingStatus status) {
        this.status = status;
    }
}
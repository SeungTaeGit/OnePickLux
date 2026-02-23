package com.onepicklux.domain.product.entity;

import com.onepicklux.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inspection")
public class Inspection extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inspection_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    @Column(nullable = false)
    private String inspectorName;

    private String leatherStatus;
    private String hardwareStatus;
    private String shapeStatus;
    private String innerStatus;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String finalComment;

    @Builder
    public Inspection(Product product, String inspectorName, String leatherStatus, String hardwareStatus, String shapeStatus, String innerStatus, String finalComment) {
        this.product = product;
        this.inspectorName = inspectorName;
        this.leatherStatus = leatherStatus;
        this.hardwareStatus = hardwareStatus;
        this.shapeStatus = shapeStatus;
        this.innerStatus = innerStatus;
        this.finalComment = finalComment;
    }
}
package com.onepicklux.domain.product.entity;

import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
@SQLDelete(sql = "UPDATE product SET deleted_at = NOW() WHERE product_id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    private ProductGrade grade;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String thumbnailUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @Builder
    public Product(Member seller, Brand brand, Category category, String name, int price, ProductGrade grade, ProductStatus status, String description, String thumbnailUrl) {
        this.seller = seller;
        this.brand = brand;
        this.category = category;
        this.name = name;
        this.price = price;
        this.grade = grade;
        this.status = status;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
    }

    public void updateInfo(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public void changeStatus(ProductStatus status) {
        this.status = status;
    }

    public void update(Brand brand, Category category, String name, Integer price, ProductGrade grade, ProductStatus status, String description, String thumbnailUrl) {
        this.brand = brand;
        this.category = category;
        this.name = name;
        this.price = price;
        this.grade = grade;
        this.status = status;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
    }
}
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
@SQLDelete(sql = "UPDATE product SET is_deleted = true, deleted_at = NOW() WHERE product_id = ?")
@SQLRestriction("is_deleted = false")
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
    @Column(name = "product_type", nullable = false)
    private ProductType type;

    @Enumerated(EnumType.STRING)
    private ProductGrade grade;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String thumbnailUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @Column(nullable = false)
    private Integer discountRate = 0;

    @Column(nullable = false)
    private int viewCount = 0;

    @Column(nullable = false)
    private boolean isDeleted = false;

    @Builder
    public Product(Member seller, Brand brand, Category category, String name, int price,
                   ProductType type, ProductGrade grade, ProductStatus status, String description,
                   String thumbnailUrl, Integer discountRate) {
        this.seller = seller;
        this.brand = brand;
        this.category = category;
        this.name = name;
        this.price = price;
        this.type = (type != null) ? type : ProductType.PRE_OWNED;
        this.grade = grade;
        this.status = status;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.discountRate = (discountRate != null) ? discountRate : 0;
        this.viewCount = 0;
        this.isDeleted = false;
    }

    public void updateInfo(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public void changeStatus(ProductStatus status) {
        this.status = status;
    }

    public void update(Brand brand, Category category, String name, Integer price,
                       ProductType type, ProductGrade grade, ProductStatus status, String description, String thumbnailUrl) { // 💡 type 추가
        this.brand = brand;
        this.category = category;
        this.name = name;
        this.price = price;
        if (type != null) this.type = type;
        this.grade = grade;
        this.status = status;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
    }

    public void updateInfo(Brand brand, Category category, String name, Integer price,
                           Integer discountRate, ProductStatus status,
                           ProductType type, ProductGrade grade, String description) {
        this.brand = brand;
        this.category = category;
        this.name = name;
        this.price = price;
        this.discountRate = discountRate;
        this.status = status;
        if (type != null) this.type = type;
        if (grade != null) this.grade = grade;
        if (description != null) this.description = description;
    }

    public void updateInfo(Brand brand, Category category, String name, Integer price,
                           Integer discountRate, ProductStatus status,
                           ProductType type, ProductGrade grade, String description, String thumbnailUrl) {
        updateInfo(brand, category, name, price, discountRate, status, type, grade, description);
        if (thumbnailUrl != null) this.thumbnailUrl = thumbnailUrl;
    }

    public void softDelete() {
        this.isDeleted = true;
    }

    public void addViewCount() {
        this.viewCount++;
    }

    public void addDetailImage(ProductImage image) {
        this.images.add(image);
    }
}
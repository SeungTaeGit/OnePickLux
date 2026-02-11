package com.onepicklux.domain.cart.entity;

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
@Table(name = "cart")
public class Cart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int count;

    @Builder
    public Cart(Member member) {
        this.member = member;
        this.count = 0;
    }

    public static Cart createCart(Member member) {
        return Cart.builder()
                .member(member)
                .build();
    }
}
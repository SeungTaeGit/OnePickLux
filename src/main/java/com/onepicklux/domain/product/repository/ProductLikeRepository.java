package com.onepicklux.domain.product.repository;

import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.domain.product.entity.Product;
import com.onepicklux.domain.product.entity.ProductLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    boolean existsByMemberAndProduct(Member member, Product product);

    Optional<ProductLike> findByMemberAndProduct(Member member, Product product);

    long countByProduct(Product product);

    @Query("SELECT pl.product.id FROM ProductLike pl WHERE pl.member.id = :memberId")
    List<Long> findLikedProductIdsByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT pl.product FROM ProductLike pl " +
            "JOIN FETCH pl.product.brand " +
            "JOIN FETCH pl.product.category " +
            "WHERE pl.member.id = :memberId " +
            "ORDER BY pl.createdAt DESC")
    List<Product> findLikedProductsByMemberId(@Param("memberId") Long memberId);
}
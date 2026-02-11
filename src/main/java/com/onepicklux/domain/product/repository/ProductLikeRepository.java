package com.onepicklux.domain.product.repository;

import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.domain.product.entity.Product;
import com.onepicklux.domain.product.entity.ProductLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    boolean existsByMemberAndProduct(Member member, Product product);

    Optional<ProductLike> findByMemberAndProduct(Member member, Product product);

    long countByProduct(Product product);
}
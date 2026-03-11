package com.onepicklux.domain.product.repository;

import com.onepicklux.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findAllByOrderByCreatedAtDesc();

    @Query("SELECT COALESCE(SUM(p.price), 0) FROM Product p WHERE p.status = 'SELLING' AND p.isDeleted = false")
    Long sumOriginalPriceOfSellingProducts();

    @Query("SELECT COALESCE(SUM(p.price * (100 - COALESCE(p.discountRate, 0)) / 100), 0) FROM Product p WHERE p.status = 'SELLING' AND p.isDeleted = false")
    Long sumDiscountedPriceOfSellingProducts();

    @Query("SELECT COUNT(p) FROM Product p WHERE p.status = 'SOLD_OUT' AND p.isDeleted = false")
    Long countSoldOutProducts();
}
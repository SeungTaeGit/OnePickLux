package com.onepicklux.domain.product.repository;

import com.onepicklux.domain.product.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
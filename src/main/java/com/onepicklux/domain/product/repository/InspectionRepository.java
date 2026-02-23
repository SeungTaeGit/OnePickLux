package com.onepicklux.domain.product.repository;

import com.onepicklux.domain.product.entity.Inspection;
import com.onepicklux.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InspectionRepository extends JpaRepository<Inspection, Long> {

    Optional<Inspection> findByProduct(Product product);

}
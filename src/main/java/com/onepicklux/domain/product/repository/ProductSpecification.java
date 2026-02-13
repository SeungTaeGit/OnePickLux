package com.onepicklux.domain.product.repository;

import com.onepicklux.domain.product.dto.ProductSearchCondition;
import com.onepicklux.domain.product.entity.Product;
import com.onepicklux.domain.product.entity.ProductStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class ProductSpecification {

    public static Specification<Product> search(ProductSearchCondition condition) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(condition.getKeyword())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + condition.getKeyword() + "%"));
            }

            if (condition.getCategoryId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), condition.getCategoryId()));
            }

            if (condition.getBrandId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("brand").get("id"), condition.getBrandId()));
            }

            if (condition.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), condition.getMinPrice()));
            }
            if (condition.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), condition.getMaxPrice()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
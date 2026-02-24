package com.onepicklux.domain.product.repository;

import com.onepicklux.domain.product.dto.ProductSearchCondition;
import com.onepicklux.domain.product.entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> search(ProductSearchCondition condition) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(condition.getKeyword())) {
                Predicate nameMatch = criteriaBuilder.like(root.get("name"), "%" + condition.getKeyword() + "%");
                Predicate brandMatch = criteriaBuilder.like(root.join("brand").get("name"), "%" + condition.getKeyword() + "%");
                predicates.add(criteriaBuilder.or(nameMatch, brandMatch));
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

            predicates.add(criteriaBuilder.equal(root.get("isDeleted"), false));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
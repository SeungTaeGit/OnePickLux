package com.onepicklux.domain.selling.repository;

import com.onepicklux.domain.selling.entity.SellingRequest;
import com.onepicklux.domain.selling.entity.SellingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellingRequestRepository extends JpaRepository<SellingRequest, Long> {
    List<SellingRequest> findByMemberIdOrderByCreatedAtDesc(Long memberId);

    long countByStatus(SellingStatus status);
}
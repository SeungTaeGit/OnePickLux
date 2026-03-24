package com.onepicklux.domain.member.repository;

import com.onepicklux.domain.member.entity.PointHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    List<PointHistory> findByMemberIdOrderByCreatedAtDesc(Long memberId);

    Page<PointHistory> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
package com.onepicklux.domain.inquiry.repository;

import com.onepicklux.domain.inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    @Query("SELECT i FROM Inquiry i JOIN FETCH i.member WHERE i.member.id = :memberId ORDER BY i.createdAt DESC")
    List<Inquiry> findAllByMemberIdOrderByCreatedAtDesc(@Param("memberId") Long memberId);

    @Query("SELECT i FROM Inquiry i JOIN FETCH i.member ORDER BY i.createdAt DESC")
    List<Inquiry> findAllByOrderByCreatedAtDesc();
}
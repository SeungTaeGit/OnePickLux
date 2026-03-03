package com.onepicklux.domain.admin.repository;

import com.onepicklux.domain.admin.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    List<Banner> findAllByOrderByCreatedAtDesc();

    List<Banner> findByIsActiveTrueOrderByCreatedAtDesc();
}
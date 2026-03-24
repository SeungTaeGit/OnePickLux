package com.onepicklux.domain.admin.service;

import com.onepicklux.domain.admin.dto.AdminPointDto;
import com.onepicklux.domain.member.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminPointService {

    private final PointHistoryRepository pointHistoryRepository;

    public Page<AdminPointDto.PointLogResponse> getAllPointLogs(Pageable pageable) {
        return pointHistoryRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(AdminPointDto.PointLogResponse::from);
    }
}
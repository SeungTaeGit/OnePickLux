package com.onepicklux.domain.product.service;

import com.onepicklux.domain.admin.dto.BrandDto;
import com.onepicklux.domain.product.entity.Brand;
import com.onepicklux.domain.product.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BrandService {

    private final BrandRepository brandRepository;

    public List<BrandDto.Response> getActiveBrands() {
        return brandRepository.findAll().stream()
                .filter(Brand::isDisplay)
                .map(BrandDto.Response::from)
                .collect(Collectors.toList());
    }
}
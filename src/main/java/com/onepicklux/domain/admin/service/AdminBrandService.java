package com.onepicklux.domain.admin.service;

import com.onepicklux.domain.admin.dto.BrandDto;
import com.onepicklux.domain.product.entity.Brand;
import com.onepicklux.domain.product.repository.BrandRepository;
import com.onepicklux.global.common.S3UploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminBrandService {

    private final BrandRepository brandRepository;
    private final S3UploaderService s3UploaderService;

    public List<BrandDto.Response> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(BrandDto.Response::from)
                .collect(Collectors.toList());
    }

    public List<BrandDto.Response> getActiveBrands() {
        return brandRepository.findAll().stream()
                .filter(Brand::isDisplay)
                .map(BrandDto.Response::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public BrandDto.Response createBrand(BrandDto.Request request, MultipartFile logoImage) {
        String logoUrl = null;
        if (logoImage != null && !logoImage.isEmpty()) {
            logoUrl = s3UploaderService.uploadImage(logoImage);
        }

        Brand brand = Brand.builder()
                .englishName(request.getEnglishName())
                .koreanName(request.getKoreanName())
                .isDisplay(request.isDisplay())
                .logoUrl(logoUrl)
                .build();

        brandRepository.save(brand);
        return BrandDto.Response.from(brand);
    }

    @Transactional
    public BrandDto.Response updateBrand(Long brandId, BrandDto.Request request, MultipartFile logoImage) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다."));

        String newLogoUrl = null;
        if (logoImage != null && !logoImage.isEmpty()) {
            if (brand.getLogoUrl() != null) {
                s3UploaderService.deleteImage(brand.getLogoUrl());
            }
            newLogoUrl = s3UploaderService.uploadImage(logoImage);
        }

        brand.updateInfo(request.getEnglishName(), request.getKoreanName(), newLogoUrl, request.isDisplay());

        return BrandDto.Response.from(brand);
    }

    @Transactional
    public void deleteBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다."));

        if (brand.getLogoUrl() != null) {
            s3UploaderService.deleteImage(brand.getLogoUrl());
        }

        brandRepository.delete(brand);
    }
}
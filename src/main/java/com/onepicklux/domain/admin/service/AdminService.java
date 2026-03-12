package com.onepicklux.domain.admin.service;

import com.onepicklux.domain.admin.dto.AdminDashboardResponse;
import com.onepicklux.domain.admin.dto.AdminProductUpdateRequest;
import com.onepicklux.domain.admin.dto.AdminResponseDto;
import com.onepicklux.domain.member.repository.MemberRepository;
import com.onepicklux.domain.product.entity.*;
import com.onepicklux.domain.product.repository.BrandRepository;
import com.onepicklux.domain.product.repository.CategoryRepository;
import com.onepicklux.domain.product.repository.ProductRepository;
import com.onepicklux.domain.selling.entity.SellingStatus;
import com.onepicklux.domain.selling.repository.SellingRequestRepository;
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
public class AdminService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final SellingRequestRepository sellingRequestRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final S3UploaderService s3UploaderService;

    public AdminDashboardResponse getDashboardStats() {
        Long sumOriginal = productRepository.sumOriginalPriceOfSellingProducts();
        Long sumDiscounted = productRepository.sumDiscountedPriceOfSellingProducts();
        Long soldOutCount = productRepository.countSoldOutProducts();

        return AdminDashboardResponse.builder()
                .totalMembers(memberRepository.count())
                .totalProducts(productRepository.count())
                .pendingSellingRequests(sellingRequestRepository.countByStatus(SellingStatus.REQUESTED))
                .todayNewOrders(0)

                .totalOriginalInventoryValue(sumOriginal)
                .totalDiscountedInventoryValue(sumDiscounted)
                .soldOutCount(soldOutCount)
                .todayRevenue(0)
                .build();
    }

    public List<AdminResponseDto.AdminProductResponse> getAllProducts() {
        return productRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(product -> AdminResponseDto.AdminProductResponse.builder()
                        .productId(product.getId())
                        .brandName(product.getBrand() != null ?
                                product.getBrand().getKoreanName() + " (" + product.getBrand().getEnglishName() + ")" : "Unknown")
                        .name(product.getName())
                        .price(product.getPrice())
                        .discountRate(product.getDiscountRate())
                        // .stock(product.getStock())
                        .status(product.getStatus().name())
                        .thumbnailUrl(product.getThumbnailUrl())
                        .createdAt(product.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public List<AdminResponseDto.AdminSellingRequestResponse> getAllSellingRequests() {
        return sellingRequestRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(request -> AdminResponseDto.AdminSellingRequestResponse.builder()
                        .requestId(request.getId())
                        .userName(request.getMember().getName())
                        .requestType(request.getRequestType().getDescription())
                        .brandName(request.getBrandName())
                        .itemName(request.getItemName())
                        .expectedPrice(request.getPurchasePrice())
                        .status(request.getStatus().getDescription())
                        .requestedAt(request.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateProduct(Long productId, AdminProductUpdateRequest request,
                              MultipartFile thumbnail, List<MultipartFile> detailImages) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 브랜드입니다."));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        String updatedThumbnailUrl = product.getThumbnailUrl();
        if (thumbnail != null && !thumbnail.isEmpty()) {
            if (product.getThumbnailUrl() != null) {
                s3UploaderService.deleteImage(product.getThumbnailUrl());
            }
            updatedThumbnailUrl = s3UploaderService.uploadImage(thumbnail);
        }

        product.updateInfo(
                brand,
                category,
                request.getName(),
                request.getPrice(),
                request.getDiscountRate(),
                request.getStatus(),
                request.getGrade(),
                request.getDescription(),
                updatedThumbnailUrl
        );

        if (detailImages != null && !detailImages.isEmpty()) {

            for (ProductImage oldImage : product.getImages()) {
                s3UploaderService.deleteImage(oldImage.getImageUrl());
            }

            product.getImages().clear();

            for (MultipartFile file : detailImages) {
                if (file != null && !file.isEmpty()) {
                    String detailUrl = s3UploaderService.uploadImage(file);
                    ProductImage newImage = ProductImage.builder()
                            .product(product)
                            .imageUrl(detailUrl)
                            .build();
                    product.addDetailImage(newImage);
                }
            }
        }
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        product.softDelete();
    }

    @Transactional
    public void registerDirectProduct(Long brandId, Long categoryId, String name, Integer price,
                                      Integer discountRate, String status, String grade,
                                      String description, String thumbnailUrl) {

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다."));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        Product product = Product.builder()
                .brand(brand)
                .category(category)
                .name(name)
                .price(price)
                .discountRate(discountRate)
                .status(ProductStatus.valueOf(status))
                .grade(ProductGrade.valueOf(grade))
                .description(description)
                .thumbnailUrl(thumbnailUrl)
                .build();

        productRepository.save(product);
    }
}
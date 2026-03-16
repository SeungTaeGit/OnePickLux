package com.onepicklux.domain.product.service;

import com.onepicklux.domain.product.dto.ProductRequest;
import com.onepicklux.domain.product.dto.ProductResponse;
import com.onepicklux.domain.product.dto.ProductSearchCondition;
import com.onepicklux.domain.product.dto.ProductUpdateRequest;
import com.onepicklux.domain.product.entity.*;
import com.onepicklux.domain.product.repository.*;
import com.onepicklux.global.common.S3UploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductLikeRepository productLikeRepository;
    private final S3UploaderService s3UploaderService;

    @Transactional
    public ProductResponse createProduct(ProductRequest request, MultipartFile thumbnail, List<MultipartFile> detailImages) {
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("해당 브랜드를 찾을 수 없습니다."));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));

        String thumbnailUrl = null;
        if (thumbnail != null && !thumbnail.isEmpty()) {
            thumbnailUrl = s3UploaderService.uploadImage(thumbnail);
        }

        Product product = Product.builder()
                .brand(brand)
                .category(category)
                .name(request.getName())
                .price(request.getPrice())
                .grade(request.getGrade())
                .status(request.getStatus())
                .description(request.getDescription())
                .discountRate(request.getDiscountRate())
                .thumbnailUrl(thumbnailUrl)
                .build();

        if (detailImages != null && !detailImages.isEmpty()) {
            int order = 1;
            for (MultipartFile file : detailImages) {
                if (!file.isEmpty()) {
                    String detailUrl = s3UploaderService.uploadImage(file);
                    ProductImage image = ProductImage.builder()
                            .product(product)
                            .imageUrl(detailUrl)
                            .displayOrder(order++)
                            .build();
                    product.getImages().add(image);
                }
            }
        }

        Product savedProduct = productRepository.save(product);
        return ProductResponse.of(savedProduct, false);
    }

    public Page<ProductResponse> getProducts(ProductSearchCondition condition, Pageable pageable, Long memberId) {
        Specification<Product> spec = ProductSpecification.search(condition);
        Page<Product> products = productRepository.findAll(spec, pageable);

        if (memberId == null) {
            return products.map(product -> ProductResponse.of(product, false));
        }

        List<Long> likedProductIds = productLikeRepository.findLikedProductIdsByMemberId(memberId);
        return products.map(product -> {
            boolean isLiked = likedProductIds.contains(product.getId());
            return ProductResponse.of(product, isLiked);
        });
    }

    @Transactional
    public ProductResponse getProduct(Long productId, Long memberId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));

        product.addViewCount();

        if (memberId == null) {
            return ProductResponse.of(product, false);
        }

        boolean isLiked = productLikeRepository.findLikedProductIdsByMemberId(memberId).contains(productId);
        return ProductResponse.of(product, isLiked);
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest request, MultipartFile thumbnail, List<MultipartFile> detailImages) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("해당 브랜드를 찾을 수 없습니다."));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));

        String updatedThumbnailUrl = product.getThumbnailUrl();
        if (thumbnail != null && !thumbnail.isEmpty()) {
            s3UploaderService.deleteImage(product.getThumbnailUrl());
            updatedThumbnailUrl = s3UploaderService.uploadImage(thumbnail);
        }

        product.update(brand, category, request.getName(), request.getPrice(), request.getType(),
                request.getGrade(), request.getStatus(), request.getDescription(), updatedThumbnailUrl);

        if (detailImages != null && !detailImages.isEmpty()) {
            for (ProductImage oldImage : product.getImages()) {
                s3UploaderService.deleteImage(oldImage.getImageUrl());
            }
            product.getImages().clear();

            int order = 1;
            for (MultipartFile file : detailImages) {
                if (!file.isEmpty()) {
                    String detailUrl = s3UploaderService.uploadImage(file);
                    ProductImage image = ProductImage.builder()
                            .product(product)
                            .imageUrl(detailUrl)
                            .displayOrder(order++)
                            .build();
                    product.getImages().add(image);
                }
            }
        }

        return ProductResponse.of(product, false);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));

        s3UploaderService.deleteImage(product.getThumbnailUrl());

        for (ProductImage productImage : product.getImages()) {
            s3UploaderService.deleteImage(productImage.getImageUrl());
        }

        productRepository.delete(product);
    }
}
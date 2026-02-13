package com.onepicklux.domain.product.service;

import com.onepicklux.domain.product.dto.ProductRequest;
import com.onepicklux.domain.product.dto.ProductResponse;
import com.onepicklux.domain.product.dto.ProductSearchCondition;
import com.onepicklux.domain.product.dto.ProductUpdateRequest;
import com.onepicklux.domain.product.entity.*;
import com.onepicklux.domain.product.repository.BrandRepository;
import com.onepicklux.domain.product.repository.CategoryRepository;
import com.onepicklux.domain.product.repository.ProductRepository;
import com.onepicklux.domain.product.repository.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("해당 브랜드를 찾을 수 없습니다."));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));

        Product product = request.toEntity(brand, category);

        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
            List<String> urls = request.getImageUrls();
            for (int i = 0; i < urls.size(); i++) {
                ProductImage image = ProductImage.builder()
                        .product(product)
                        .imageUrl(urls.get(i))
                        .displayOrder(i + 1)
                        .build();
                product.getImages().add(image);
            }
        }

        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    public Page<ProductResponse> getProducts(ProductSearchCondition condition, Pageable pageable) {
        Specification<Product> spec = ProductSpecification.search(condition);

        return productRepository.findAll(spec, pageable)
                .map(ProductResponse::of);
    }


    public ProductResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));

        return ProductResponse.of(product);
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("해당 브랜드를 찾을 수 없습니다."));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));

        product.update(brand, category, request.getName(), request.getPrice(),
                request.getGrade(), request.getStatus(), request.getDescription(), request.getThumbnailUrl());

        if (request.getImageUrls() != null) {
            product.getImages().clear();

            List<String> urls = request.getImageUrls();
            for (int i = 0; i < urls.size(); i++) {
                ProductImage image = ProductImage.builder()
                        .product(product)
                        .imageUrl(urls.get(i))
                        .displayOrder(i + 1)
                        .build();
                product.getImages().add(image);
            }
        }

        return ProductResponse.of(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));

        productRepository.delete(product);
    }
}
package com.onepicklux.domain.admin.dto;

import com.onepicklux.domain.product.entity.ProductGrade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminSellingApproveRequest {

    @NotNull(message = "브랜드 ID는 필수입니다.")
    private Long brandId;

    @NotNull(message = "카테고리 ID는 필수입니다.")
    private Long categoryId;

    @NotBlank(message = "정제된 상품명은 필수입니다.")
    private String name;

    @NotNull(message = "판매 책정 가격은 필수입니다.")
    private Integer price;

    @NotNull(message = "최종 상품 등급은 필수입니다.")
    private ProductGrade grade;

    private String description;

    @NotBlank(message = "담당 검수자 이름은 필수입니다.")
    private String inspectorName;

    private String leatherStatus;
    private String hardwareStatus;
    private String shapeStatus;
    private String innerStatus;

    @NotBlank(message = "검수자 종합 소견은 필수입니다.")
    private String finalComment;
}
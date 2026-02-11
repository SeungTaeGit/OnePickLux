package com.onepicklux.domain.selling.dto;

import com.onepicklux.domain.member.entity.Member;
import com.onepicklux.domain.selling.entity.SellingRequest;
import com.onepicklux.domain.selling.entity.SellingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SellingRequestDto {

    @NotNull(message = "판매 방식은 필수입니다.")
    private SellingType requestType;

    @NotBlank(message = "브랜드명은 필수입니다.")
    private String brandName;

    @NotBlank(message = "상품명은 필수입니다.")
    private String itemName;

    private String purchaseYear;

    private Integer purchasePrice;

    private String imageUrl;

    public SellingRequest toEntity(Member member) {
        return SellingRequest.builder()
                .member(member)
                .requestType(requestType)
                .brandName(brandName)
                .itemName(itemName)
                .purchaseYear(purchaseYear)
                .purchasePrice(purchasePrice)
                .imageUrl(imageUrl)
                .build();
    }
}
package com.example.shoppingMall.mapper;

import com.example.shoppingMall.dto.request.ProductVariationsRequest;
import com.example.shoppingMall.dto.response.ProductVariationResponse;
import com.example.shoppingMall.model.ProductVariation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductVariationMapper {

    /** request -> entity */
    @Mapping(target = "variationId", ignore = true)      // new variation, id do DB sinh
    ProductVariation toEntity(ProductVariationsRequest req);

    /** entity -> response (trả về FE) */
    ProductVariationResponse toResponse(ProductVariation variation);

    /** tiện ích merge khi update (nếu cần) */
    void updateEntity(ProductVariationsRequest req,
                      @MappingTarget ProductVariation target);
}

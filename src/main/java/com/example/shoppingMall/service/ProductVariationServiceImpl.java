package com.example.shoppingMall.service;

import com.example.shoppingMall.dao.ProductVariationDao;
import com.example.shoppingMall.dto.request.ProductVariationsRequest;
import com.example.shoppingMall.dto.response.ProductVariationResponse;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.exception.ErrorCode;
import com.example.shoppingMall.mapper.ProductVariationMapper;
import com.example.shoppingMall.model.ProductVariation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductVariationServiceImpl implements ProductVariationService {

    @Autowired
    private ProductVariationDao productVariationDao;
    @Autowired
    private ProductVariationMapper productVariationMapper;

    /* ---------------------- QUERY ---------------------- */

    @Override
    public List<ProductVariationResponse> getVariationsByProductId(long productId) {
        return productVariationDao.findByProductId(productId)
                .stream()
                .map(productVariationMapper::toResponse)
                .toList();
    }

    @Override
    public Map<String, List<String>> getOptionInputsOfProduct(long productId) {
        return productVariationDao.getOptionInputsOfProduct(productId);
    }

    /* ---------------------- COMMAND ---------------------- */

    @Override
    public ProductVariationResponse addVariation(ProductVariationsRequest request) {
        ProductVariation entity = productVariationMapper.toEntity(request);
        ProductVariation saved   = productVariationDao.addVariation(entity);
        return productVariationMapper.toResponse(saved);
    }

    @Override
    public ProductVariationResponse updateVariation(long variationId, ProductVariationsRequest request) {
        // convert request -> entity
        ProductVariation entity = productVariationMapper.toEntity(request);
        ProductVariation updated = productVariationDao.updateVariation(variationId, entity);
        if (updated == null) throw new AppException(ErrorCode.VARIATION_NOT_FOUND);
        return productVariationMapper.toResponse(updated);
    }

    @Override
    public void deleteVariation(long variationId) {
        productVariationDao.deleteVariation(variationId);
    }
}

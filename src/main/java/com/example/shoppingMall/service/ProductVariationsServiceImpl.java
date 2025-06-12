package com.example.shoppingMall.service;

import com.example.shoppingMall.dao.ProductVariationsDao;
import com.example.shoppingMall.dto.response.ProductVariationsResponse;
import com.example.shoppingMall.mapper.ProductVariationsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductVariationsServiceImpl implements ProductVariationsService {
    @Autowired
    private ProductVariationsDao productVariationsDao;
    @Autowired
    private ProductVariationsMapper productVariationsMapper;

    @Override
    public List<ProductVariationsResponse> getAllProductVariations(long productId) {
        return productVariationsDao.getAllProductVariations(productId)
                .stream()
                .map(productVariationsMapper::toProductVariationsResponse)
                .toList();
    }
}

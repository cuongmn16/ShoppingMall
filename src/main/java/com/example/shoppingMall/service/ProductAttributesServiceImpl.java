package com.example.shoppingMall.service;

import com.example.shoppingMall.dao.ProductAttributesDao;
import com.example.shoppingMall.dto.response.ProductAttributesResponse;
import com.example.shoppingMall.mapper.ProductAttributesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAttributesServiceImpl implements ProductAttributesService {
    @Autowired
    private ProductAttributesDao productAttributesDao;
    @Autowired
    private ProductAttributesMapper productAttributesMapper;


    @Override
    public List<ProductAttributesResponse> getAllProductAttributes(long productId) {
        return productAttributesDao.getAllProductAttributes(productId)
                .stream()
                .map(productAttributesMapper::toProductAttributesResponse)
                .toList();
    }
}

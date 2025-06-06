package com.example.shoppingMall.service;

import com.example.shoppingMall.dao.ProductImagesDao;
import com.example.shoppingMall.dto.response.ProductImagesResponse;
import com.example.shoppingMall.mapper.ProductImagesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImagesServiceImpl implements ProductImagesService {

    @Autowired
    private ProductImagesDao productImagesDao;

    @Autowired
    private ProductImagesMapper productImagesMapper;



    @Override
    public List<ProductImagesResponse> getAllProductImages(long productId) {

        return productImagesDao.getAllProductImages(productId)
                .stream()
                .map(productImagesMapper::toProductImagesResponse)
                .toList();
    }
}

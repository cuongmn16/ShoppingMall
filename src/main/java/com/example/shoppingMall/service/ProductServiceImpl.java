package com.example.shoppingMall.service;


import com.example.shoppingMall.dao.ProductDao;
import com.example.shoppingMall.dto.request.ProductRequest;
import com.example.shoppingMall.dto.response.ProductResponse;
import com.example.shoppingMall.mapper.ProductMapper;
import com.example.shoppingMall.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductResponse> getAllProducts(int pageNumber, int pageSize) {
        return productDao.getAllProducts(pageNumber, pageSize).stream().map(productMapper::toProduct).toList();
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productMapper.toProductRequest(productRequest);
        return productMapper.toProduct( productDao.addProduct(product));
    }


}

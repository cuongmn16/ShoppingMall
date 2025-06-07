package com.example.shoppingMall.service;

import com.example.shoppingMall.dao.ProductImagesDao;
import com.example.shoppingMall.dto.request.ProductImagesRequest;
import com.example.shoppingMall.dto.response.ProductImagesResponse;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.exception.ErrorCode;
import com.example.shoppingMall.mapper.ProductImagesMapper;
import com.example.shoppingMall.model.ProductImages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.util.List;

@Service
public class ProductImagesServiceImpl implements ProductImagesService {

    @Autowired
    private ProductImagesDao productImagesDao;

    @Autowired
    private ProductImagesMapper productImagesMapper;
    @Autowired
    private View error;


    @Override
    public List<ProductImagesResponse> getAllProductImages(long productId) {
        return productImagesDao.getAllProductImages(productId)
                .stream()
                .map(productImagesMapper::toProductImagesResponse)
                .toList();
    }

    @Override
    public ProductImagesResponse addProductImage(long productId, ProductImagesRequest productImagesRequest) {
        ProductImages productImages = productImagesMapper.toProductImages(productImagesRequest);
        productImagesDao.addProductImage(productId, productImages);
        return productImagesMapper.toProductImagesResponse(productImages);
    }


    @Override
    public ProductImagesResponse updateProductImage(long productId, long imageId, ProductImagesRequest productImagesRequest) {
        ProductImages productImages = productImagesDao.getProductImageById(productId, imageId);
        if (productImages == null || !productImagesDao.isProductImageExistsById(productId, imageId)) {
            throw new AppException(ErrorCode.PRODUCT_IMAGE_NOT_FOUND);
        }
        ProductImages updatedProductImage = productImagesMapper.toProductImages(productImagesRequest);
        productImagesDao.updateProductImage(productId, imageId, updatedProductImage);
        return null;
    }

    @Override
    public void deleteProductImage(long productId, long imageId) {
        if (!productImagesDao.isProductImageExistsById(productId, imageId)) {
            throw new AppException(ErrorCode.PRODUCT_IMAGE_NOT_FOUND);
        }
        productImagesDao.deleteProductImage(productId, imageId);
    }

    @Override
    public ProductImagesResponse getProductImageById(long productId, long imageId) {
        ProductImages productImages = productImagesDao.getProductImageById(productId, imageId);
        if (productImages != null || !productImagesDao.isProductImageExistsById(productId, imageId)) {
            throw new AppException(ErrorCode.PRODUCT_IMAGE_NOT_FOUND);
        }
        return productImagesMapper.toProductImagesResponse(productImages);
    }
}

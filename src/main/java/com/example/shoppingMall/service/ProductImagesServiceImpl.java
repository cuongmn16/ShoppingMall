package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.ProductImagesRequest;
import com.example.shoppingMall.dto.response.ProductImagesResponse;
import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.exception.ErrorCode;
import com.example.shoppingMall.mapper.ProductImagesMapper;
import com.example.shoppingMall.entity.ProductImages;
import com.example.shoppingMall.repository.ProductImagesRepository;
import com.example.shoppingMall.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.util.List;
import java.util.UUID;
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductImagesServiceImpl implements ProductImagesService {


    ProductImagesRepository productImagesRepository;


    ProductImagesMapper productImagesMapper;

    View error;
    ProductRepository productRepository;


    @Override
    public List<ProductImagesResponse> getAllProductImages(UUID productId) {
        return productImagesRepository.getAllImagesByProductId(productId)
                .stream()
                .map(productImagesMapper::toProductImagesResponse)
                .toList();
    }

    @Override
    public ProductImagesResponse addProductImage(UUID productId, ProductImagesRequest productImagesRequest) {
        ProductImages productImages = productImagesMapper.toProductImages(productImagesRequest);
        Product product = productRepository.findById(productId)
                        .orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        productImages.setProduct(product);

        return productImagesMapper.toProductImagesResponse(productImagesRepository.save(productImages));
    }


    @Override
    public ProductImagesResponse updateProductImage(UUID productId, UUID imageId, ProductImagesRequest productImagesRequest) {
        ProductImages productImages = productImagesRepository.getProductImageByProductIdandImageId(productId, imageId);
        if (productImages == null || !productImagesRepository.isProductImageExistsByProductIdandImageId(productId, imageId)) {
            throw new AppException(ErrorCode.PRODUCT_IMAGE_NOT_FOUND);
        }
        ProductImages updatedProductImage = productImagesMapper.toProductImages(productImagesRequest);
        productImagesRepository.save(updatedProductImage);
        return null;
    }

    @Override
    public void deleteProductImage(UUID productId, UUID imageId) {
        if (!productImagesRepository.isProductImageExistsByProductIdandImageId(productId, imageId)) {
            throw new AppException(ErrorCode.PRODUCT_IMAGE_NOT_FOUND);
        }
        productImagesRepository.deleteProductImage(productId, imageId);
    }

    @Override
    public ProductImagesResponse getProductImageById(UUID productId, UUID imageId) {
        ProductImages productImages = productImagesRepository.getProductImageByProductIdandImageId(productId, imageId);
        if (productImages != null || !productImagesRepository.isProductImageExistsByProductIdandImageId(productId, imageId)) {
            throw new AppException(ErrorCode.PRODUCT_IMAGE_NOT_FOUND);
        }
        return productImagesMapper.toProductImagesResponse(productImages);
    }
}

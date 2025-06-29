package com.example.shoppingMall.service;


import com.example.shoppingMall.dao.ProductDao;
import com.example.shoppingMall.dto.request.ProductRequest;
import com.example.shoppingMall.dto.response.*;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.exception.ErrorCode;
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
    @Autowired
    private ProductImagesService productImagesService;


    @Override
    public List<ProductResponse> getAllProducts(int pageNumber, int pageSize) {
        return productDao.getAllProducts(pageNumber, pageSize)
                .stream().map(productMapper::toProduct).toList();
    }

    @Override
    public List<ProductResponse> getAllProductsByCategoryId(long categoryId, int pageNumber, int pageSize) {
        return productDao.getAllProductsByCategory(categoryId, pageNumber, pageSize)
                .stream().map(productMapper::toProduct).toList();

    }

    @Override
    public ProductDetailResponse getProductDetail(long productId) {
        ProductDetailResponse product = productDao.getProductById(productId);
        if (product == null){
            throw  new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        ProductDetailResponse productDetailResponse = new ProductDetailResponse();
        productDetailResponse.setProductId(product.getProductId());
        productDetailResponse.setSellerId(product.getSellerId());
        productDetailResponse.setCategoryId(product.getCategoryId());
        productDetailResponse.setProductName(product.getProductName());
        productDetailResponse.setDescription(product.getDescription());
        productDetailResponse.setPrice(product.getPrice());
        productDetailResponse.setOriginalPrice(product.getOriginalPrice());
        productDetailResponse.setDiscount(product.getDiscount());
        productDetailResponse.setStockQuantity(product.getStockQuantity());
        productDetailResponse.setSoldQuantity(product.getSoldQuantity());
        productDetailResponse.setRating(product.getRating());
        productDetailResponse.setProductStatus(product.getProductStatus());
        productDetailResponse.setShopName(product.getShopName());
        productDetailResponse.setShopDescription(product.getShopDescription());
        productDetailResponse.setShopLogo(product.getShopLogo());

        List<ProductImagesResponse> productImages = productImagesService.getAllProductImages(productId);
        productDetailResponse.setProductImages(productImages);


        return productDetailResponse;


    }

    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = productMapper.toProductRequest(productRequest);
        return productMapper.toProduct( productDao.addProduct(product));
    }

    @Override
    public ProductResponse updateProduct(long productId, ProductRequest productRequest) {
        // Chuyển đổi DTO sang entity
        Product product = productMapper.toProductRequest(productRequest);
        product.setProductId(productId);

        // Gọi DAO update
        Product updatedProduct = productDao.updateProduct(productId, product);

        if (updatedProduct == null) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        return productMapper.toProduct(updatedProduct);
    }


    @Override
    public List<ProductResponse> findProductsByKeyword(String keyword) {
        return productDao.findProductsByKeyword(keyword)
                .stream()
                .map(productMapper::toProduct)
                .toList();
    }

    @Override
    public List<ProductResponse> getRecommendedProducts(int limit) {
        return productDao.getRecommendedProducts(limit)
                .stream()
                .map(productMapper::toProduct)
                .toList();
    }


}

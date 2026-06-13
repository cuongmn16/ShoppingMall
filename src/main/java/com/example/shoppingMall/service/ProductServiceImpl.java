package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.ProductRequest;
import com.example.shoppingMall.dto.response.*;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.exception.ErrorCode;
import com.example.shoppingMall.mapper.ProductMapper;
import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.repository.ProductRepository;
import com.example.shoppingMall.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImagesService productImagesService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserRepository userRepository;



    @Override
    public List<ProductResponse> getAllProducts(String search, String category, String stock, int pageNumber, int pageSize) {
//        Page<Product> products = productRepository.getAllProducts(search, category, stock, pageNumber, pageSize);
//        List<ProductResponse> productResponses = products.stream()
//                .map(productMapper::toProduct)
//                .toList();
//
//        // Tính toán stats
//        long totalProducts = productRepository.getAllProducts(search, category, stock, 1, Integer.MAX_VALUE).;
//        double totalValue = products.stream()
//                .mapToDouble(p -> p.getPrice() * p.getStockQuantity())
//                .sum();
//        long lowStockCount = products.stream()
//                .filter(p -> p.getStockQuantity() < 10)
//                .count();
//
//        Map<String, Object> stats = new HashMap<>();
//        stats.put("totalProducts", totalProducts);
//        stats.put("totalValue", totalValue);
//        stats.put("lowStockCount", lowStockCount);


        return null;
    }
    @Override
    public List<ProductResponse> getAllProductsByCategoryId(long categoryId, int pageNumber, int pageSize) {
        Pageable pageable =
                PageRequest.of(pageNumber, pageSize);
        return productRepository.getAllProductsByCategory(categoryId, pageable)
                .stream().map(productMapper::toProduct).toList();

    }

    @Override
    public ProductDetailResponse getProductDetail(long productId) {
        return null;
    }

//    @Override
//    public ProductDetailResponse getProductDetail(UUID productId) {
//        return productRepository.findById(productId)
//                .orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
//
//    }

    @Override
    public ProductResponse addProduct(HttpServletRequest request, ProductRequest productRequest) {
        UUID userId = request.
        Product product = productMapper.toProductRequest(productRequest);
        return productMapper.toProduct( productRepository.save(product));
    }

    @Override
    public ProductResponse updateProduct( ProductRequest productRequest) {
        Product product = productMapper.toProductRequest(productRequest);
        // Gọi DAO update
        Product updatedProduct = productRepository.save( product);

        if (updatedProduct == null) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        return productMapper.toProduct(updatedProduct);
    }


    @Override
    public List<ProductResponse> findProductsByKeyword(String keyword) {
        return productRepository.findProductsByKeyword(keyword)
                .stream()
                .map(productMapper::toProduct)
                .toList();
    }

    @Override
    public List<ProductResponse> getRecommendedProducts(int limit) {
        return productRepository.getRecommendedProducts(limit)
                .stream()
                .map(productMapper::toProduct)
                .toList();
    }


}

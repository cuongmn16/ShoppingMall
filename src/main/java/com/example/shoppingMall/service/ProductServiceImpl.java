package com.example.shoppingMall.service;


import com.example.shoppingMall.dao.ProductDao;
import com.example.shoppingMall.dao.UserDao;
import com.example.shoppingMall.dto.request.ProductRequest;
import com.example.shoppingMall.dto.response.*;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.exception.ErrorCode;
import com.example.shoppingMall.mapper.ProductMapper;
import com.example.shoppingMall.model.Product;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImagesService productImagesService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserDao userDao;


    @Override
    public List<ProductResponse> getAllProducts(String search, String category, String stock, int pageNumber, int pageSize) {
        List<Product> products = productDao.getAllProducts(search, category, stock, pageNumber, pageSize);
        List<ProductResponse> productResponses = products.stream()
                .map(productMapper::toProduct)
                .toList();

        // Tính toán stats
        long totalProducts = productDao.getAllProducts(search, category, stock, 1, Integer.MAX_VALUE).size(); // Tổng số sản phẩm
        double totalValue = products.stream()
                .mapToDouble(p -> p.getPrice() * p.getStockQuantity())
                .sum();
        long lowStockCount = products.stream()
                .filter(p -> p.getStockQuantity() < 10)
                .count();

        // Thêm stats vào response (cần tạo một DTO mới nếu cần)
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProducts", totalProducts);
        stats.put("totalValue", totalValue);
        stats.put("lowStockCount", lowStockCount);

        // Lưu stats vào context hoặc trả về cùng response (tùy chỉnh ApiResponse)
        return productResponses; // Hiện tại chỉ trả productResponses, cần điều chỉnh ApiResponse
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
    public ProductResponse addProduct(HttpServletRequest request, ProductRequest productRequest) {
        String username = authenticationService.extractUsernameFromRequest(request);
        long sellerId = userDao.getSellerIdByUsername(username);
        System.out.println("Seller ID: " + sellerId);
        Product product = productMapper.toProductRequest(productRequest);
        return productMapper.toProduct( productDao.addProduct(sellerId,product));
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

package com.example.shoppingMall.admin.adminService;

import com.example.shoppingMall.admin.adminDao.ManageProductDao;
import com.example.shoppingMall.dao.UserDao;
import com.example.shoppingMall.dto.response.ProductResponse;
import com.example.shoppingMall.mapper.ProductMapper;
import com.example.shoppingMall.model.Product;
import com.example.shoppingMall.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManageProductServiceImpl implements  ManageProductService {
    @Autowired
    private ManageProductDao manageProductDao;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<ProductResponse> getAllProductsBySellerId(HttpServletRequest request, String search, String category, String stock) {

        String username = authenticationService.extractUsernameFromRequest(request);
        long sellerId = userDao.getSellerIdByUsername(username);
        List<Product> products = manageProductDao.getAllProductsBySellerId(sellerId,search, category, stock);
        List<ProductResponse> productResponses = products.stream()
                .map(productMapper::toProduct)
                .toList();

        // Tính toán stats
        long totalProducts = manageProductDao.getAllProductsBySellerId(sellerId,search, category, stock).size(); // Tổng số sản phẩm
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
        return productResponses;

    }
}

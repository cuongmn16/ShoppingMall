package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> getAllProductsByCategory(long categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "(:search IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:category IS NULL OR p.category = :category) ") // Thay đổi tên trường (p.name, p.category, p.stockStatus) cho đúng với Entity Product của bạn nhé
    Page<Product> getAllProducts(
            @Param("search") String search,
            @Param("category") String category,
            Pageable pageable
    );

    // Sửa lại hàm findProductsByKeyword như sau:
    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> findProductsByKeyword(@Param("keyword") String keyword);

    // Ví dụ: Lấy ngẫu nhiên các sản phẩm (Native Query cho MySQL/H2)
    @Query(value = "SELECT * FROM product ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Product> getRecommendedProducts(@Param("limit") int limit);
}

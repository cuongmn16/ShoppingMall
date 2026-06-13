package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import jakarta.transaction.Transactional;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImages, UUID> {
    @Query("SELECT p FROM ProductImages p WHERE p.product.productId = :productId")
    List<ProductImages> getAllImagesByProductId(@Param("productId") UUID productId);

    @Query("SELECT p FROM ProductImages p WHERE p.product.productId = :productId AND p.imageId = :imageId")
    ProductImages getProductImageByProductIdandImageId(@Param("productId") UUID productId, @Param("imageId") UUID imageId);

    @Query("SELECT COUNT(p) > 0 FROM ProductImages p WHERE p.product.productId = :productId AND p.imageId = :imageId")
    boolean isProductImageExistsByProductIdandImageId(@Param("productId") UUID productId, @Param("imageId") UUID imageId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductImages p WHERE p.product.productId = :productId AND p.imageId = :imageId")
    void deleteProductImage(@Param("productId") UUID productId, @Param("imageId") UUID imageId);
}

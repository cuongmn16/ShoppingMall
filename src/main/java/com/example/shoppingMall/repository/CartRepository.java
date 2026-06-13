package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
//    @Query("""
//    SELECT c
//                            FROM carts c
//                            LEFT JOIN cart_items ci
//                                ON c.cart_id = ci.cart_id
//                            LEFT JOIN product p
//                                ON ci.product_id = p.product_id
//                            WHERE c.user_id = :userId
//""")
//    Optional<Cart> findByUserId(UUID userId);
}

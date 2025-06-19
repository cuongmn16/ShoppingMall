package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.response.ApiResponse;
import com.example.shoppingMall.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping
    public ApiResponse<List<CartResponse>> getAllCarts(HttpServletRequest httpServletRequest){
        String username = authenticationService.extractUsernameFromRequest(httpServletRequest);
        List<CartResponse> cartResponses = cartService.getAllCarts(username);
        ApiResponse<List<CartResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cartResponses);
        apiResponse.setMessage("Cart items retrieved successfully");
        return apiResponse;
    }

    @PostMapping()
    public ApiResponse<CartResponse> addToCart(HttpServletRequest httpServletRequest,@RequestBody CartRequest cartRequest){
        String username = authenticationService.extractUsernameFromRequest(httpServletRequest);
        CartResponse cartResponse = cartService.addToCart(username, cartRequest);
        ApiResponse<CartResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cartResponse);
        apiResponse.setMessage("Cart item added successfully");
        return apiResponse;

    }

    @DeleteMapping("/{cartId}")
    public ApiResponse<Void> deleteCart(@PathVariable long cartId) {
        cartService.deleteCart(cartId);
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Cart item deleted successfully");
        return apiResponse;
    }

}

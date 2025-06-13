package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.request.CartRequest;
import com.example.shoppingMall.dto.response.ApiResponse;
import com.example.shoppingMall.dto.response.CartResponse;
import com.example.shoppingMall.service.AuthenticationService;
import com.example.shoppingMall.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping()
    public ApiResponse<CartResponse> addToCart(HttpServletRequest httpServletRequest,@RequestBody CartRequest cartRequest){
        String username = authenticationService.extractUsernameFromRequest(httpServletRequest);
        CartResponse cartResponse = cartService.addToCart(username, cartRequest);
        ApiResponse<CartResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cartResponse);
        apiResponse.setMessage("Cart item added successfully");
        return apiResponse;

    }
}

package com.example.shoppingMall.mapper;

import com.example.shoppingMall.dto.request.CartRequest;
import com.example.shoppingMall.dto.response.CartResponse;
import com.example.shoppingMall.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartResponse toCartResponse(Cart cart);

    Cart toCartRequest(CartRequest cartRequest);
}

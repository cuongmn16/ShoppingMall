package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.request.ShopCategoriesRequest;
import com.example.shoppingMall.dto.response.ShopCategoriesResponse;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.exception.ErrorCode;
import com.example.shoppingMall.mapper.ShopCategoriesMapper;
import com.example.shoppingMall.entity.ShopCategories;
import com.example.shoppingMall.repository.ShopCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShopCategoriesServiceImpl implements ShopCategoriesService {
    @Autowired
    private ShopCategoriesRepository shopCategoriesRepository;
    @Autowired
    private ShopCategoriesMapper shopCategoriesMapper;

    @Override
    public List<ShopCategoriesResponse> getAllShopCategories() {
        return shopCategoriesRepository.findAll().stream().map(shopCategoriesMapper::toShopCategoriesResponse).toList();
    }

    @Override
    public ShopCategoriesResponse getShopCategoryById(UUID categoryId) {
        ShopCategories shopCategories = shopCategoriesRepository.findById(categoryId)
                .orElseThrow(()-> new AppException(ErrorCode.SHOPCATEGORY_NOT_FOUND));
        return shopCategoriesMapper.toShopCategoriesResponse(shopCategories);
    }

    @Override
    public ShopCategoriesResponse createShopCategory(ShopCategoriesRequest shopCategoriesRequest) {
        ShopCategories shopCategories = shopCategoriesMapper.toShopCategories(shopCategoriesRequest);
        shopCategoriesRepository.save(shopCategories);
        return shopCategoriesMapper.toShopCategoriesResponse(shopCategories);
    }

    @Override
    public ShopCategoriesResponse updateShopCategory(UUID categoryId, ShopCategoriesRequest shopCategoriesRequest) {
        ShopCategories shopCategories = shopCategoriesRepository.findById(categoryId)
                .orElseThrow(()-> new AppException(ErrorCode.SHOPCATEGORY_NOT_FOUND));

        shopCategoriesMapper.toShopCategoriesUpdate(shopCategories,shopCategoriesRequest);
        return shopCategoriesMapper.toShopCategoriesResponse( shopCategoriesRepository.save(shopCategories));
    }

    @Override
    public void deleteShopCategory(UUID categoryId) {
        if (!shopCategoriesRepository.existsById(categoryId)) {
            throw new AppException(ErrorCode.SHOPCATEGORY_NOT_FOUND);
        }
        shopCategoriesRepository.deleteById(categoryId);

    }

//    @Override
//    public List<ShopCategoriesResponse> getAllShopCategoriesByParentId(Long parentId) {
//        return shopCategoriesRepository.getAllShopCategoriesByParentId(parentId)
//                .stream()
//                .map(shopCategoriesMapper::toShopCategoriesResponse)
//                .toList();
//    }

//    @Override
//    public List<ShopCategoriesResponse> getCategoriesByParentNull() {
//        List<ShopCategories> entities = shopCategoriesRepository.findCategoriesByParentNull();
//        return entities.stream()
//                .map(shopCategoriesMapper::toShopCategoriesResponse)
//                .collect(Collectors.toList());
//    }


}

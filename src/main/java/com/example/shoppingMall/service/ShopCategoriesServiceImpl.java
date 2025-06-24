package com.example.shoppingMall.service;

import com.example.shoppingMall.dao.ShopCategoriesDao;
import com.example.shoppingMall.dto.request.ShopCategoriesRequest;
import com.example.shoppingMall.dto.response.ShopCategoriesResponse;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.exception.ErrorCode;
import com.example.shoppingMall.mapper.ShopCategoriesMapper;
import com.example.shoppingMall.model.ShopCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopCategoriesServiceImpl implements ShopCategoriesService {
    @Autowired
    private ShopCategoriesDao shopCategoriesDao;
    @Autowired
    private ShopCategoriesMapper shopCategoriesMapper;

    @Override
    public List<ShopCategoriesResponse> getAllShopCategories() {
        return shopCategoriesDao.getAllShopCategories().stream().map(shopCategoriesMapper::toShopCategoriesResponse).toList();
    }

    @Override
    public ShopCategoriesResponse getShopCategoryById(long categoryId) {
        ShopCategories shopCategories = shopCategoriesDao.getShopCategoryById(categoryId);
        if(shopCategories == null || !shopCategoriesDao.isShopCategoryExistsById(categoryId)) {
            throw new AppException(ErrorCode.SHOPCATEGORY_NOT_FOUND);
        }
        return shopCategoriesMapper.toShopCategoriesResponse(shopCategories);
    }

    @Override
    public ShopCategoriesResponse createShopCategory(ShopCategoriesRequest shopCategoriesRequest) {
        ShopCategories shopCategories = shopCategoriesMapper.toShopCategories(shopCategoriesRequest);
        shopCategoriesDao.createShopCategory(shopCategories);
        return shopCategoriesMapper.toShopCategoriesResponse(shopCategories);
    }

    @Override
    public void updateShopCategory(long categoryId, ShopCategoriesRequest shopCategoriesRequest) {
        ShopCategories shopCategories = shopCategoriesDao.getShopCategoryById(categoryId);
        if (shopCategories == null || !shopCategoriesDao.isShopCategoryExistsById(categoryId)) {
            throw new AppException(ErrorCode.SHOPCATEGORY_NOT_FOUND);
        }
        ShopCategories updatedShopCategory = shopCategoriesMapper.toShopCategories(shopCategoriesRequest);
        shopCategoriesDao.updateShopCategory(categoryId,updatedShopCategory);

    }

    @Override
    public void deleteShopCategory(long categoryId) {
        if (!shopCategoriesDao.isShopCategoryExistsById(categoryId)) {
            throw new AppException(ErrorCode.SHOPCATEGORY_NOT_FOUND);
        }
        shopCategoriesDao.deleteShopCategory(categoryId);

    }

    @Override
    public List<ShopCategoriesResponse> getAllShopCategoriesByParentId(Long parentId) {
        return shopCategoriesDao.getAllShopCategoriesByParentId(parentId)
                .stream()
                .map(shopCategoriesMapper::toShopCategoriesResponse)
                .toList();
    }

    @Override
    public List<ShopCategoriesResponse> getCategoriesByParentNull() {
        List<ShopCategories> entities = shopCategoriesDao.findCategoriesByParentNull();
        return entities.stream()
                .map(shopCategoriesMapper::toShopCategoriesResponse)
                .collect(Collectors.toList());
    }


}

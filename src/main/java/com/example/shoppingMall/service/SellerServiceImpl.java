package com.example.shoppingMall.service;

import com.example.shoppingMall.dao.SellerDao;
import com.example.shoppingMall.dto.request.SellerRequest;
import com.example.shoppingMall.dto.response.SellerResponse;
import com.example.shoppingMall.exception.AppException;
import com.example.shoppingMall.exception.ErrorCode;
import com.example.shoppingMall.mapper.SellerMapper;
import com.example.shoppingMall.model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerServiceImpl implements  SellerService {
    @Autowired
    private SellerDao sellerDao;
    @Autowired
    private SellerMapper sellerMapper;

    @Override
    public List<SellerResponse> getAllSellers() {
        return sellerDao.getAllSellers().stream()
                .map(sellerMapper::toSellerResponse)
                .toList();
    }

    @Override
    public SellerResponse createSeller(SellerRequest sellerRequest) {
        var seller = sellerMapper.toSellerRequest(sellerRequest);
        sellerDao.createSeller(seller);
        return sellerMapper.toSellerResponse(seller);
    }

    @Override
    public SellerResponse getSellerById(long sellerId) {
        Seller seller = sellerDao.getSellerById(sellerId);
        if (seller == null || !sellerDao.isSellerExists(sellerId)) {
            throw new AppException(ErrorCode.SELLER_NOT_FOUND);
        }
        return sellerMapper.toSellerResponse(seller);
    }

    @Override
    public SellerResponse updateSeller(long sellerId, Seller seller) {
        Seller existingSeller = sellerDao.getSellerById(sellerId);
        if (existingSeller == null || !sellerDao.isSellerExists(sellerId)) {
            throw new AppException(ErrorCode.SELLER_NOT_FOUND);
        }
        Seller updatedSeller = sellerDao.updateSeller(sellerId, seller);
        return sellerMapper.toSellerResponse(updatedSeller);
    }

    @Override
    public boolean isSellerExists(long sellerId) {
         return false;
    }

    @Override
    public void deleteSeller(long sellerId) {
        if (!sellerDao.isSellerExists(sellerId)) {
            throw new AppException(ErrorCode.SELLER_NOT_FOUND);
        }
        sellerDao.deleteSeller(sellerId);

    }
}

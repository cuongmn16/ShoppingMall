package com.example.shoppingMall.service;

import com.example.shoppingMall.dao.SellerDao;
import com.example.shoppingMall.dto.request.SellerRequest;
import com.example.shoppingMall.dto.response.SellerResponse;
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
}

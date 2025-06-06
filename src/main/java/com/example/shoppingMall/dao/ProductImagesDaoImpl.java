package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.ProductImages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductImagesDaoImpl implements ProductImagesDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<ProductImages> getAllProductImages(long productId) {
        return null;
    }

    @Override
    public ProductImages addProductImage(long productId, ProductImages productImages) {
        return null;
    }

    @Override
    public ProductImages updateProductImage(long productId, long imageId, ProductImages productImages) {
        return null;
    }

    @Override
    public void deleteProductImage(long productId, long imageId) {

    }
}

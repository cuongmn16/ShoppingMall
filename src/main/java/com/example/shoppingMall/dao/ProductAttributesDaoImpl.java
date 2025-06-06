package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.ProductAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductAttributesDaoImpl implements ProductAttributesDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<ProductAttributes> getAllProductAttributes(long productId) {
        return List.of();
    }

    @Override
    public ProductAttributes addProductAttribute(long productId, ProductAttributes productAttributes) {
        return null;
    }

    @Override
    public ProductAttributes updateProductAttribute(long productId, long attributeId, ProductAttributes productAttributes) {
        return null;
    }

    @Override
    public void deleteProductAttribute(long productId, long attributeId) {

    }
}

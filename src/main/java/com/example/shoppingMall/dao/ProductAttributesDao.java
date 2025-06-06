package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.ProductAttributes;

import java.util.List;

public interface ProductAttributesDao {
    List<ProductAttributes> getAllProductAttributes(long productId);

    ProductAttributes addProductAttribute(long productId, ProductAttributes productAttributes);

    ProductAttributes updateProductAttribute(long productId, long attributeId, ProductAttributes productAttributes);

    void deleteProductAttribute(long productId, long attributeId);

}

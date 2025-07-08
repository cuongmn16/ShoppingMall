package com.example.shoppingMall.dao;

import com.example.shoppingMall.model.ProductVariation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProductVariationDao {
    List<ProductVariation> findByProductId(long productId);

    Map<String, List<String>> getOptionInputsOfProduct(long productId);

    List<String> fetchOptionInputs(Connection conn, long variationId) throws SQLException;

    ProductVariation addVariation(ProductVariation variation);

    ProductVariation updateVariation(long variationId, ProductVariation variation);

    void deleteVariation(long variationId);

}

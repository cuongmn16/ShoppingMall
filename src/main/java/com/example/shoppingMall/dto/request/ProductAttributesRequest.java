package com.example.shoppingMall.dto.request;

public class ProductAttributesRequest {
    private long productId;
    private String attributeName;
    private String attributeValue;

    public ProductAttributesRequest() {
    }

    public ProductAttributesRequest(long productId, String attributeName, String attributeValue) {
        this.productId = productId;
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
}

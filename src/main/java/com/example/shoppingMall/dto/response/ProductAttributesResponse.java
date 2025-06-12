package com.example.shoppingMall.dto.response;

public class ProductAttributesResponse {
    private long attributeId;
    private long productId;
    private String attributeName;
    private String attributeValue;

    public ProductAttributesResponse() {
    }

    public ProductAttributesResponse(long attributeId, long productId, String attributeName, String attributeValue) {
        this.attributeId = attributeId;
        this.productId = productId;
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    public long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(long attributeId) {
        this.attributeId = attributeId;
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

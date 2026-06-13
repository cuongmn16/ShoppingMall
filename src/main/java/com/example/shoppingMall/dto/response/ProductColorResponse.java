package com.example.shoppingMall.dto.response;

public class ProductColorResponse {
    private long OptionValueId;
    private String color;

    public ProductColorResponse() {
    }

    public ProductColorResponse(long optionValueId, String color) {
        this.OptionValueId = optionValueId;
        this.color = color;
    }

    public long getOptionValueId() {
        return OptionValueId;
    }

    public void setOptionValueId(long optionValueId) {
        OptionValueId = optionValueId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
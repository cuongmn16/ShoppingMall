package com.example.shoppingMall.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionValue {
    private long optionValueId;
    private long optionTypeId;
    private String value;

    public OptionValue() {
    }

    public OptionValue(long optionValueId, long optionTypeId, String value) {
        this.optionValueId = optionValueId;
        this.optionTypeId = optionTypeId;
        this.value = value;
    }

    public long getOptionValueId() {
        return optionValueId;
    }

    public void setOptionValueId(long optionValueId) {
        this.optionValueId = optionValueId;
    }

    public long getOptionTypeId() {
        return optionTypeId;
    }

    public void setOptionTypeId(long optionTypeId) {
        this.optionTypeId = optionTypeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

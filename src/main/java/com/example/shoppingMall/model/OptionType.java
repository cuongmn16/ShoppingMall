package com.example.shoppingMall.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionType {
    private long optionTypeId;
    private String name;

    public OptionType() {
    }

    public OptionType(long optionTypeId, String name) {
        this.optionTypeId = optionTypeId;
        this.name = name;
    }

    public long getOptionTypeId() {
        return optionTypeId;
    }

    public void setOptionTypeId(long optionTypeId) {
        this.optionTypeId = optionTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

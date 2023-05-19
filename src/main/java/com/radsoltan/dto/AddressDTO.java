package com.radsoltan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressDTO {
    @JsonProperty("3wa")
    private String threeWordAddress;

    public AddressDTO(String threeWordAddress) {
        this.threeWordAddress = threeWordAddress;
    }

    public String getThreeWordAddress() {
        return threeWordAddress;
    }

    public void setThreeWordAddress(String threeWordAddress) {
        this.threeWordAddress = threeWordAddress;
    }
}

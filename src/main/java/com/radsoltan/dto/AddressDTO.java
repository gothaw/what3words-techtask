package com.radsoltan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that describes 3 Word Address.
 */
public class AddressDTO {
    @JsonProperty("3wa")
    private String threeWordAddress;

    public String getThreeWordAddress() {
        return threeWordAddress;
    }

    public void setThreeWordAddress(String threeWordAddress) {
        this.threeWordAddress = threeWordAddress;
    }
}

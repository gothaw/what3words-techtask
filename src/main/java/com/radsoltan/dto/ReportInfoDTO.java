package com.radsoltan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportInfoDTO {
    @JsonProperty("message")
    private String message;
    @JsonProperty("lat")
    private Double latitude;
    @JsonProperty("lng")
    private Double longitude;
    @JsonProperty("3wa")
    private String threeWordAddress;
    @JsonProperty("reportingOfficerName")
    private String reportingOfficerName;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getThreeWordAddress() {
        return threeWordAddress;
    }

    public void setThreeWordAddress(String threeWordAddress) {
        this.threeWordAddress = threeWordAddress;
    }

    public String getReportingOfficerName() {
        return reportingOfficerName;
    }

    public void setReportingOfficerName(String reportingOfficerName) {
        this.reportingOfficerName = reportingOfficerName;
    }
}

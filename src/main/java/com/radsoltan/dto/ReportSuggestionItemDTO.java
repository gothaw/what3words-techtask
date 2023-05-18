package com.radsoltan.dto;

public class ReportSuggestionItemDTO {
    private final String country;
    private final String nearestPlace;
    private final String words;

    public ReportSuggestionItemDTO(String country, String nearestPlace, String words) {
        this.country = country;
        this.nearestPlace = nearestPlace;
        this.words = words;
    }

    public String getCountry() {
        return country;
    }

    public String getNearestPlace() {
        return nearestPlace;
    }

    public String getWords() {
        return words;
    }
}

package com.radsoltan.dto;

public class ReportSuggestionsItemDTO {
    private final String country;
    private final String nearestPlace;
    private final String words;

    public ReportSuggestionsItemDTO(String country, String nearestPlace, String words) {
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

package com.radsoltan.dto;

public class ReportDTO {
    private final ReportInfoDTO info;
    private final ReportSuggestionsDTO suggestion;

    public ReportDTO(ReportInfoDTO info, ReportSuggestionsDTO suggestion) {
        this.info = info;
        this.suggestion = suggestion;
    }

    public ReportInfoDTO getInfo() {
        return info;
    }

    public ReportSuggestionsDTO getSuggestion() {
        return suggestion;
    }
}

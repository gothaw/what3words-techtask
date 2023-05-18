package com.radsoltan.dto;

public class ReportDTO {
    private final ReportInfoDTO info;
    private final ReportSuggestionDTO suggestion;

    public ReportDTO(ReportInfoDTO info, ReportSuggestionDTO suggestion) {
        this.info = info;
        this.suggestion = suggestion;
    }

    public ReportInfoDTO getInfo() {
        return info;
    }

    public ReportSuggestionDTO getSuggestion() {
        return suggestion;
    }
}

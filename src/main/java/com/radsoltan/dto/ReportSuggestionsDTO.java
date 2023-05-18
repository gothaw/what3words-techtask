package com.radsoltan.dto;

import java.util.List;

public class ReportSuggestionsDTO {
    private String message;
    private List<ReportSuggestionsItemDTO> suggestions;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ReportSuggestionsItemDTO> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<ReportSuggestionsItemDTO> suggestions) {
        this.suggestions = suggestions;
    }
}

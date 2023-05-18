package com.radsoltan.dto;

import java.util.List;

public class ReportSuggestionDTO {
    private String message;
    private List<ReportSuggestionItemDTO> suggestions;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ReportSuggestionItemDTO> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<ReportSuggestionItemDTO> suggestions) {
        this.suggestions = suggestions;
    }
}

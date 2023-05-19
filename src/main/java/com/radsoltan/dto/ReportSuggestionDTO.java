package com.radsoltan.dto;

import com.what3words.javawrapper.response.Suggestion;

import java.util.List;

public class ReportSuggestionDTO {
    private String message;
    private List<Suggestion> suggestions;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }
}

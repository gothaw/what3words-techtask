package com.radsoltan.dto;

import com.what3words.javawrapper.response.Suggestion;

import java.util.List;

/**
 * Class includes all the information included in report suggestions, which is included in a response if user provided a 3wa that is not recognized.
 * This could be 3wa address that is not in GB or a string that was not found but matches the regex pattern.
 */
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

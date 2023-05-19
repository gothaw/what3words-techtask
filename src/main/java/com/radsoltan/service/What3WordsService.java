package com.radsoltan.service;

import com.radsoltan.dto.ReportDTO;
import com.radsoltan.dto.ReportInfoDTO;
import com.radsoltan.dto.ReportSuggestionDTO;
import com.radsoltan.exception.MissingReportInfoException;
import com.radsoltan.util.Constants;
import com.radsoltan.util.Validation;
import com.radsoltan.util.What3WordsApi;
import com.what3words.javawrapper.What3WordsV3;
import com.what3words.javawrapper.response.Autosuggest;
import com.what3words.javawrapper.response.Coordinates;
import com.what3words.javawrapper.response.Suggestion;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class What3WordsService {

    public ReportDTO validateAndFillEmergencyReport(ReportInfoDTO info) {
        ReportSuggestionDTO suggestions = new ReportSuggestionDTO();
        if (!Validation.isValidReportInfo(info)) {
            throw new MissingReportInfoException();
        } else {
            What3WordsV3 api = What3WordsApi.getInstance();
            Double latitude = info.getLatitude();
            Double longitude = info.getLongitude();
            String address = info.getThreeWordAddress();

            if (latitude == null || longitude == null) {
                // Get coordinates based on 3 word address
                Coordinates coordinates = What3WordsApi.getCoordinatesBasedOnAddress(api, address);
                // Get suggestions
                Autosuggest autosuggest = What3WordsApi.getAutoSuggest(api, address, Constants.LANGUAGE_EN, Constants.COUNTRY_GB);
                List<Suggestion> suggestionList = autosuggest.getSuggestions();

                if (coordinates != null && What3WordsApi.isGbAddress(address, suggestionList)) {
                    info.setLatitude(coordinates.getLat());
                    info.setLongitude(coordinates.getLng());
                } else {
                    suggestions.setMessage(Constants.THREE_WORD_ADDRESS_NOT_RECOGNIZED + address);
                    suggestions.setSuggestions(suggestionList);
                }
            } else if (!StringUtils.hasText(address)) {
                // Get 3 word address based on coordinates
                com.what3words.javawrapper.request.Coordinates coordinates = new com.what3words.javawrapper.request.Coordinates(latitude, longitude);
                address = What3WordsApi.getAddressBasedOnCoordinates(api, coordinates, Constants.LANGUAGE_EN);
                info.setThreeWordAddress(address);
            }
        }
        return new ReportDTO(info, suggestions);
    }
}

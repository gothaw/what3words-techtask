package com.radsoltan.service;

import com.radsoltan.dto.AddressDTO;
import com.radsoltan.dto.ReportDTO;
import com.radsoltan.dto.ReportInfoDTO;
import com.radsoltan.dto.ReportSuggestionDTO;
import com.radsoltan.exception.InvalidAddressFormatException;
import com.radsoltan.exception.InvalidReportInformationException;
import com.radsoltan.exception.MissingReportInfoException;
import com.radsoltan.exception.NotUkCoordinatesException;
import com.radsoltan.util.Constants;
import com.radsoltan.util.Validation;
import com.radsoltan.util.What3WordsApi;
import com.what3words.javawrapper.What3WordsV3;
import com.what3words.javawrapper.response.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service used to handle emergency report submission (prefilling and checking the report) and converting 3wa address from one English to Welsh and vice versa.
 */
@Service
public class What3WordsService {

    /**
     * The method checks and fills emergency report. It uses following rules:
     * 1. It checks if either valid 3wa or a set of coordinates were provided
     *   - If none were provided it throws an exception.
     * 2. If 3wa was provided it checks if it's in valid format. If not it throws an exception
     * 3. If a complete set of coordinates was NOT provided
     *   - It gets coordinates from the API
     *   - If coordinates for that 3wa address exist and are in the UK it adds them to the report
     *   - Otherwise, it prepares a list of suggestions based on 3wa provided
     * 4. Otherwise, if coordinates were provided:
     *   - It gets 3wa based on coordinates
     *   - If 3wa was provided in the request, and it doesn't match 3wa from the response, it throws and exception.
     *   - Otherwise, if 3wa address from the response is not in the UK, it throws an exception.
     *   - Otherwise, it adds 3wa from the response to the report.
     *
     * @param info report information object
     * @return filled and checked emergency report along with 3wa suggestions (if applicable)
     */
    public ReportDTO validateAndFillEmergencyReport(ReportInfoDTO info) {
        ReportSuggestionDTO suggestions = new ReportSuggestionDTO();
        if (!Validation.isValidReportInfo(info)) {
            throw new MissingReportInfoException();
        }

        What3WordsV3 api = What3WordsApi.getInstance();
        Double latitude = info.getLatitude();
        Double longitude = info.getLongitude();
        String address = info.getThreeWordAddress();

        if (address != null && !Validation.validateThreeWordAddress(address)) {
            throw new InvalidAddressFormatException();
        }

        if (latitude == null || longitude == null) {
            // Get coordinates based on 3 word address
            ConvertToCoordinates response = What3WordsApi.getCoordinatesBasedOnThreeWordAddress(api, address);
            Coordinates coordinates = response.getCoordinates();
            // Get suggestions
            Autosuggest autosuggest = What3WordsApi.getAutoSuggest(api, address, Constants.LANGUAGE_EN, Constants.COUNTRY_GB);
            List<Suggestion> suggestionList = autosuggest.getSuggestions();

            if (coordinates != null && response.getCountry().equals(Constants.COUNTRY_GB)) {
                info.setLatitude(coordinates.getLat());
                info.setLongitude(coordinates.getLng());
            } else {
                suggestions.setMessage(Constants.THREE_WORD_ADDRESS_NOT_RECOGNIZED + address);
                suggestions.setSuggestions(suggestionList);
            }
        } else {
            // Get 3 word address based on coordinates
            com.what3words.javawrapper.request.Coordinates coordinates = new com.what3words.javawrapper.request.Coordinates(latitude, longitude);
            ConvertTo3WA response = What3WordsApi.getThreeWordAddressBasedOnCoordinates(api, coordinates, Constants.LANGUAGE_EN);
            String responseAddress = response.getWords();
            if (address != null && !address.equals(responseAddress)) {
                // Check if provided 3wa matches with the one from the response
                throw new InvalidReportInformationException();
            } else if (!response.getCountry().equals(Constants.COUNTRY_GB)) {
                throw new NotUkCoordinatesException();
            } else {
                info.setThreeWordAddress(responseAddress);
            }
        }

        return new ReportDTO(info, suggestions);
    }

    /**
     *
     * @param
     * @return
     */
    public AddressDTO getWelshThreeWordAddressFromEnglish(AddressDTO englishAddressDTO) {
        if (englishAddressDTO == null) {
            throw new RuntimeException("Three word address must not be null.");
        }
        String address = englishAddressDTO.getThreeWordAddress();

        return null;
    }
}

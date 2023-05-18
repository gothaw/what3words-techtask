package com.radsoltan.service;

import com.radsoltan.dto.ReportDTO;
import com.radsoltan.dto.ReportInfoDTO;
import com.radsoltan.dto.ReportSuggestionsDTO;
import com.radsoltan.util.Validation;
import com.radsoltan.util.What3WordsApi;
import com.what3words.javawrapper.What3WordsV3;
import com.what3words.javawrapper.response.Coordinates;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class What3WordsService {

    public ReportDTO validateAndFillEmergencyReport(ReportInfoDTO info) {
        ReportSuggestionsDTO suggestions = new ReportSuggestionsDTO();
        if (!Validation.isValidReportInfo(info)) {
            throw new RuntimeException("Invalid Report Info");
        } else {
            What3WordsV3 api = What3WordsApi.getInstance();
            Double latitude = info.getLatitude();
            Double longitude = info.getLongitude();
            String address = info.getThreeWordAddress();

            if (latitude == null || longitude == null) {
                // Get coordinates based on 3 word address
                Coordinates coordinates = What3WordsApi.getCoordinatesBasedOnAddress(api, address);

                if (coordinates != null) {
                    info.setLatitude(coordinates.getLat());
                    info.setLongitude(coordinates.getLng());
                } else {
                    // Get suggestions
                    return null;
                }
            } else if (!StringUtils.hasText(address)) {
                // Get 3 word address based on coordinates
                com.what3words.javawrapper.request.Coordinates coordinates = new com.what3words.javawrapper.request.Coordinates(latitude, longitude);
                address = What3WordsApi.getAddressBasedOnCoordinates(api, coordinates,"en");
                info.setThreeWordAddress(address);
            }
        }
        return new ReportDTO(info, suggestions);
    }
}

package com.radsoltan.service;

import com.radsoltan.dto.ReportDTO;
import com.radsoltan.dto.ReportInfoDTO;
import com.radsoltan.dto.ReportSuggestionDTO;
import com.radsoltan.exception.InvalidAddressFormatException;
import com.radsoltan.exception.InvalidReportInformationException;
import com.radsoltan.exception.LocationNotInUkException;
import com.radsoltan.exception.MissingReportInfoException;
import com.radsoltan.util.Constants;
import com.radsoltan.util.ApiWrapper;
import com.what3words.javawrapper.What3WordsV3;
import com.what3words.javawrapper.request.AutosuggestInputType;
import com.what3words.javawrapper.request.AutosuggestRequest;
import com.what3words.javawrapper.request.ConvertTo3WARequest;
import com.what3words.javawrapper.request.ConvertToCoordinatesRequest;
import com.what3words.javawrapper.response.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class What3WordsServiceTest {
    What3WordsV3 api;
    @Mock
    ApiWrapper apiWrapper;
    @InjectMocks
    What3WordsService service;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.initMocks(this);
        api = Mockito.mock(What3WordsV3.class);
        Mockito.when(apiWrapper.getInstance()).thenReturn(api);
    }

    @Test
    void shouldCompleteReportForValidThreeWordAddress() {
        String address = "daring.lion.racer";

        ReportInfoDTO info = new ReportInfoDTO();
        info.setThreeWordAddress(address);
        info.setMessage("A climber has got lost");
        info.setReportingOfficerName("Chuck Norris");
        Double expectedLatitude = 51.508341;
        Double expectedLongitude = -0.125499;
        Coordinates coordinates = Mockito.mock(Coordinates.class);

        mockApiCallsForRequestWithThreeWordAddress(info.getThreeWordAddress(), expectedLatitude, expectedLongitude, coordinates);

        ReportDTO report = service.validateAndFillEmergencyReport(info);
        ReportInfoDTO actualInfo = report.info();
        ReportSuggestionDTO actualSuggestion = report.suggestion();

        assertEquals(expectedLatitude, actualInfo.getLatitude());
        assertEquals(expectedLongitude, actualInfo.getLongitude());
        assertEquals(address, actualInfo.getThreeWordAddress());
        assertNull(actualSuggestion.getMessage());
        assertNull(actualSuggestion.getSuggestions());
    }

    @Test
    void shouldCompleteReportForValidCoordinates() {
        Double longitude = -0.125499;
        Double latitude = 51.508341;

        ReportInfoDTO info = new ReportInfoDTO();
        info.setLongitude(longitude);
        info.setLatitude(latitude);
        info.setMessage("A climber has got lost");
        info.setReportingOfficerName("Chuck Norris");
        String expectedAddress = "daring.lion.racer";

        mockApiCallForRequestsWithCoordinates(expectedAddress, Constants.LANGUAGE_EN, Constants.COUNTRY_GB);

        ReportDTO report = service.validateAndFillEmergencyReport(info);
        ReportInfoDTO actualInfo = report.info();
        ReportSuggestionDTO actualSuggestion = report.suggestion();

        assertEquals(expectedAddress, actualInfo.getThreeWordAddress());
        assertEquals(longitude, actualInfo.getLongitude());
        assertEquals(latitude, actualInfo.getLatitude());
        assertNull(actualSuggestion.getMessage());
        assertNull(actualSuggestion.getSuggestions());
    }

    @Test
    void shouldProvideSuggestionsIf3WaIsNotRecognized() {
        String address = "daring.lion.racer";

        ReportInfoDTO info = new ReportInfoDTO();
        info.setThreeWordAddress(address);
        String expectedMessage = Constants.THREE_WORD_ADDRESS_NOT_RECOGNIZED + address;
        List<Suggestion> suggestionList = List.of(Mockito.mock(Suggestion.class));

        mockApiCallsForRequestWithThreeWordAddress(info.getThreeWordAddress(), null, null, null);
        mockApiCallForAutoSuggest(address, Constants.LANGUAGE_EN, Constants.COUNTRY_GB, suggestionList);

        ReportDTO report = service.validateAndFillEmergencyReport(info);
        ReportSuggestionDTO actualSuggestion = report.suggestion();

        assertNull(report.info().getLatitude());
        assertNull(report.info().getLongitude());
        assertEquals(expectedMessage, actualSuggestion.getMessage());
        assertEquals(suggestionList, actualSuggestion.getSuggestions());
    }

    @Test
    void shouldThrowAnExceptionIfReportInformationIsMissing() {
        ReportInfoDTO info = new ReportInfoDTO();

        assertThrows(MissingReportInfoException.class, () -> service.validateAndFillEmergencyReport(info));
    }

    @Test
    void shouldThrowAnExceptionIfAddressIsInWrongFormat() {
        String address = "xxx";
        ReportInfoDTO info = new ReportInfoDTO();
        info.setThreeWordAddress(address);

        assertThrows(InvalidAddressFormatException.class, () -> service.validateAndFillEmergencyReport(info));
    }

    @Test
    void shouldThrowAnExceptionIf3WaAddressDoesNotCorrespondToCoordinates() {
        Double longitude = -0.125499;
        Double latitude = 51.508341;

        ReportInfoDTO info = new ReportInfoDTO();
        info.setLongitude(longitude);
        info.setLatitude(latitude);
        info.setThreeWordAddress("hello.world.today");
        info.setMessage("A climber has got lost");
        info.setReportingOfficerName("Chuck Norris");
        String expectedAddress = "daring.lion.racer";

        mockApiCallForRequestsWithCoordinates(expectedAddress, Constants.LANGUAGE_EN, Constants.COUNTRY_GB);

        assertThrows(InvalidReportInformationException.class, () -> service.validateAndFillEmergencyReport(info));
    }

    @Test
    void shouldThrowAnExceptionIfProvidedCoordinatesAreNotInUK() {
        Double longitude = -25.125499;
        Double latitude = 51.508341;

        ReportInfoDTO info = new ReportInfoDTO();
        info.setLongitude(longitude);
        info.setLatitude(latitude);
        info.setMessage("A climber has got lost");
        info.setReportingOfficerName("Chuck Norris");
        String expectedAddress = "daring.lion.racer";

        mockApiCallForRequestsWithCoordinates(expectedAddress, Constants.LANGUAGE_EN, "US");

        assertThrows(LocationNotInUkException.class, () -> service.validateAndFillEmergencyReport(info));
    }

    private void mockApiCallsForRequestWithThreeWordAddress(String address, Double expectedLatitude, Double expectedLongitude, Coordinates coordinates) {
        ConvertToCoordinatesRequest.Builder builder = Mockito.mock(ConvertToCoordinatesRequest.Builder.class);
        ConvertToCoordinates mockConvert = Mockito.mock(ConvertToCoordinates.class);

        if (coordinates != null && expectedLatitude != null && expectedLongitude != null) {
            Mockito.when(coordinates.getLat()).thenReturn(expectedLatitude);
            Mockito.when(coordinates.getLng()).thenReturn(expectedLongitude);
        }
        Mockito.when(mockConvert.getCountry()).thenReturn(Constants.COUNTRY_GB);
        Mockito.when(mockConvert.getCoordinates()).thenReturn(coordinates);
        Mockito.when(builder.execute()).thenReturn(mockConvert);
        Mockito.when(api.convertToCoordinates(address)).thenReturn(builder);
    }

    private void mockApiCallForRequestsWithCoordinates(String expected3wa, String language, String country) {
        ConvertTo3WARequest.Builder builder = Mockito.mock(ConvertTo3WARequest.Builder.class);
        ConvertTo3WA mockConvert = Mockito.mock(ConvertTo3WA.class);

        Mockito.when(mockConvert.getWords()).thenReturn(expected3wa);
        Mockito.when(mockConvert.getCountry()).thenReturn(country);
        Mockito.when(builder.execute()).thenReturn(mockConvert);
        Mockito.when(builder.language(Mockito.eq(language))).thenReturn(builder);
        Mockito.when(api.convertTo3wa(Mockito.any(com.what3words.javawrapper.request.Coordinates.class))).thenReturn(builder);
    }

    private void mockApiCallForAutoSuggest(String address, String language, String country, List<Suggestion> expectedSuggestions) {
        AutosuggestRequest.Builder builder = Mockito.mock(AutosuggestRequest.Builder.class);
        Autosuggest autosuggest = Mockito.mock(Autosuggest.class);

        Mockito.when(autosuggest.getSuggestions()).thenReturn(expectedSuggestions);
        Mockito.when(builder.inputType(AutosuggestInputType.GENERIC_VOICE)).thenReturn(builder);
        Mockito.when(builder.clipToCountry(country)).thenReturn(builder);
        Mockito.when(builder.language(Mockito.eq(language))).thenReturn(builder);
        Mockito.when(builder.execute()).thenReturn(autosuggest);
        Mockito.when(api.autosuggest(address)).thenReturn(builder);
    }
}
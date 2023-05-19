package com.radsoltan.service;

import com.radsoltan.dto.ReportDTO;
import com.radsoltan.dto.ReportInfoDTO;
import com.radsoltan.dto.ReportSuggestionDTO;
import com.radsoltan.util.Constants;
import com.radsoltan.util.ApiWrapper;
import com.what3words.javawrapper.What3WordsV3;
import com.what3words.javawrapper.request.ConvertToCoordinatesRequest;
import com.what3words.javawrapper.response.ConvertToCoordinates;
import com.what3words.javawrapper.response.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
        ReportInfoDTO info = new ReportInfoDTO();
        ReportSuggestionDTO suggestion = new ReportSuggestionDTO();
        info.setLongitude(null);
        info.setLatitude(null);
        info.setThreeWordAddress("daring.lion.racer");
        info.setMessage("A climber has got lost");
        info.setReportingOfficerName("Chuck Norris");
        Double expectedLatitude = 51.508341;
        Double expectedLongitude = -0.125499;

        mockApiCallsForRequestWithThreeWordAddress(info.getThreeWordAddress(), expectedLatitude, expectedLongitude, api);

        ReportDTO report = service.validateAndFillEmergencyReport(info);
        ReportInfoDTO actualInfo = report.info();

        assertEquals(expectedLatitude, actualInfo.getLatitude());
        assertEquals(expectedLongitude, actualInfo.getLongitude());
        assertEquals(info, actualInfo);
    }

    private void mockApiCallsForRequestWithThreeWordAddress(String address, Double latitude, Double longitude, What3WordsV3 api) {
        Coordinates coordinates = Mockito.mock(Coordinates.class);
        ConvertToCoordinatesRequest.Builder builder = Mockito.mock(ConvertToCoordinatesRequest.Builder.class);
        ConvertToCoordinates mockConvert = Mockito.mock(ConvertToCoordinates.class);

        Mockito.when(coordinates.getLat()).thenReturn(latitude);
        Mockito.when(coordinates.getLng()).thenReturn(longitude);
        Mockito.when(mockConvert.getCountry()).thenReturn(Constants.COUNTRY_GB);
        Mockito.when(mockConvert.getCoordinates()).thenReturn(coordinates);
        Mockito.when(builder.execute()).thenReturn(mockConvert);
        Mockito.when(api.convertToCoordinates(Mockito.eq(address))).thenReturn(builder);
    }
}
package com.radsoltan.controller;

import com.radsoltan.dto.AddressDTO;
import com.radsoltan.dto.ReportDTO;
import com.radsoltan.dto.ReportInfoDTO;
import com.radsoltan.dto.ReportSuggestionDTO;
import com.radsoltan.service.What3WordsService;
import com.radsoltan.util.Constants;
import com.what3words.javawrapper.response.Suggestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @Mock
    What3WordsService what3WordsService;
    @InjectMocks
    Controller controller;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldReturnInfoForCompleteEmergencyReport() {
        ReportInfoDTO info = new ReportInfoDTO();
        info.setLongitude(51.508341);
        info.setLatitude(-0.125499);
        info.setThreeWordAddress("daring.lion.race");
        info.setMessage("A climber has got lost");
        info.setReportingOfficerName("Chuck Norris");

        ReportSuggestionDTO suggestions = new ReportSuggestionDTO();

        ReportDTO report = new ReportDTO(info, suggestions);

        Mockito.when(what3WordsService.validateAndFillEmergencyReport(info)).thenReturn(report);
        ResponseEntity<Object> responseEntity = controller.getEmergencyReport(info);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(info, responseEntity.getBody());
    }

    @Test
    void shouldReturnSuggestionForIncompleteEmergencyReport() {
        ReportInfoDTO info = new ReportInfoDTO();
        info.setLongitude(null);
        info.setLatitude(null);
        info.setThreeWordAddress("daring.lion.racer");
        info.setMessage("A climber has got lost");
        info.setReportingOfficerName("Chuck Norris");

        ReportSuggestionDTO suggestions = new ReportSuggestionDTO();
        Suggestion suggestion = new Suggestion();
        suggestions.setMessage("3wa not recognised: daring.lion.racer");
        suggestions.setSuggestions(List.of(suggestion));

        ReportDTO report = new ReportDTO(info, suggestions);

        Mockito.when(what3WordsService.validateAndFillEmergencyReport(info)).thenReturn(report);
        ResponseEntity<Object> responseEntity = controller.getEmergencyReport(info);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(suggestions, responseEntity.getBody());
    }

    @Test
    void shouldWorkForConvertingToWelsh() {
        String english = "daring.lion.race";
        String welsh = "sychach.parciau.lwmpyn";
        AddressDTO addressEnglish = new AddressDTO();
        AddressDTO addressWelsh = new AddressDTO();
        addressEnglish.setThreeWordAddress(english);
        addressWelsh.setThreeWordAddress(welsh);

        Mockito.when(what3WordsService.convertThreeWordAddressToProvidedLanguage(addressEnglish, Constants.LANGUAGE_WELSH)).thenReturn(addressWelsh);
        ResponseEntity<Object> responseEntity = controller.getWelshThreeWordAddress(addressEnglish);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(addressWelsh, responseEntity.getBody());
    }

    @Test
    void shouldWorkForConvertingToEnglish() {
        String english = "daring.lion.race";
        String welsh = "sychach.parciau.lwmpyn";
        AddressDTO addressEnglish = new AddressDTO();
        AddressDTO addressWelsh = new AddressDTO();
        addressEnglish.setThreeWordAddress(english);
        addressWelsh.setThreeWordAddress(welsh);

        Mockito.when(what3WordsService.convertThreeWordAddressToProvidedLanguage(addressWelsh, Constants.LANGUAGE_EN)).thenReturn(addressEnglish);
        ResponseEntity<Object> responseEntity = controller.getEnglishThreeWordAddress(addressWelsh);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(addressEnglish, responseEntity.getBody());
    }
}
package com.radsoltan.controller;

import com.radsoltan.dto.AddressDTO;
import com.radsoltan.dto.ReportDTO;
import com.radsoltan.dto.ReportInfoDTO;
import com.radsoltan.service.What3WordsService;
import com.radsoltan.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTful controller. Handles incoming HTTP requests.
 */
@RestController
public class Controller {
    @Autowired
    What3WordsService what3WordsService;

    /**
     * The endpoint that handles POST requests for submitting emergency reports.
     * It invokes method from {@code what3WordsService} that check and fill in the missing fields in the emergency report.
     *
     * @param request incomplete report information included in the request
     * @return Response with filled emergency report or suggestions if 3wa was not recognized
     */
    @RequestMapping(value = "/emergencyapi/reports", method = RequestMethod.POST)
    public ResponseEntity<Object> getEmergencyReport(@RequestBody ReportInfoDTO request) {
        ReportDTO report = what3WordsService.validateAndFillEmergencyReport(request);
        ReportInfoDTO info = report.info();

        return (Validation.isReportInfoComplete(info))
                ? ResponseEntity.ok(report.info())
                : ResponseEntity.ok(report.suggestion());
    }

    /**
     * The endpoint that handles POST request for retrieving a Welsh 3 word address based on an English one.
     * @return Response with 3wa in Welsh
     */
    @RequestMapping(value = "/emergencyapi/welsh-convert", method = RequestMethod.POST)
    public ResponseEntity<Object> getWelsh3WaLocation(@RequestBody AddressDTO request) {
        AddressDTO welshAddress = what3WordsService.getWelshThreeWordAddressFromEnglish(request);

        return ResponseEntity.ok(welshAddress);
    }

    /**
     * The endpoint that handles POST request for retrieving an English 3 word address based on a Welsh one.
     * @return Response with 3wa in English
     */
    @RequestMapping(value = "/emergencyapi/welsh-3wa", method = RequestMethod.POST)
    public ResponseEntity<Object> getWelshThreeWordAddressFromEnglish() {
        // TODO: 19/05/2023 Implement
        return ResponseEntity.ok("Ok");
    }
}

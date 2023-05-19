package com.radsoltan.controller;

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

@RestController
public class Controller {
    @Autowired
    What3WordsService what3WordsService;

    @RequestMapping(value = "/emergency-api/reports", method = RequestMethod.POST)
    public ResponseEntity<Object> getEmergencyReport(@RequestBody ReportInfoDTO request) {
        ReportDTO report = what3WordsService.validateAndFillEmergencyReport(request);
        ReportInfoDTO info = report.info();

        return (Validation.isReportInfoComplete(info))
                ? ResponseEntity.ok(report.info())
                : ResponseEntity.ok(report.suggestion());
    }

    @RequestMapping(value = "/emergency-api/welsh-convert", method = RequestMethod.POST)
    public ResponseEntity<Object> getWelsh3WaLocation() {

        return ResponseEntity.ok("Ok");
    }
}

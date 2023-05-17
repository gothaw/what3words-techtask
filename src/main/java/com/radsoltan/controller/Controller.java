package com.radsoltan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @RequestMapping(value = "/emergency-api/reports", method = RequestMethod.POST)
    public ResponseEntity<Object> getEmergencyReport() {

        return ResponseEntity.ok("Ok");
    }

    @RequestMapping(value = "/emergency-api/welsh-convert", method = RequestMethod.POST)
    public ResponseEntity<Object> getWelsh3WaLocation() {

        return ResponseEntity.ok("Ok");
    }
}

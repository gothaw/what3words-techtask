package com.radsoltan.util;

import com.radsoltan.dto.ReportInfoDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {
    @Test
    void checkingValidThreeWordAddress() {
        assertTrue(Validation.validateThreeWordAddress("abc.ATY.abc"));
        assertTrue(Validation.validateThreeWordAddress("///abc.abc.abc"));
        assertTrue(Validation.validateThreeWordAddress("//abc.xyS.aBc"));
        assertTrue(Validation.validateThreeWordAddress("/abC.abc.efg"));
    }

    @Test
    void checkingInvalidThreeWordAddresses() {
        assertFalse(Validation.validateThreeWordAddress(""));
        assertFalse(Validation.validateThreeWordAddress("acc"));
        assertFalse(Validation.validateThreeWordAddress("abc.edg.123"));
        assertFalse(Validation.validateThreeWordAddress("abd.csd.!x_+"));
    }

    @Test
    void checkingIfReportIncludesEnoughInformationToBeFilled() {
        ReportInfoDTO info1 = new ReportInfoDTO();
        ReportInfoDTO info2 = new ReportInfoDTO();
        info1.setLatitude(-10.000);
        info1.setLongitude(25.000);
        info2.setThreeWordAddress("abc.def.ghj");

        assertTrue(Validation.isValidReportInfo(info1));
        assertTrue(Validation.isValidReportInfo(info2));
    }

    @Test
    void checkingIfReportInfoIsComplete() {
        ReportInfoDTO info = new ReportInfoDTO();
        info.setLatitude(-10.000);
        info.setLongitude(25.000);
        info.setThreeWordAddress("abc.def.ghj");

        assertTrue(Validation.isReportInfoComplete(info));
    }
}
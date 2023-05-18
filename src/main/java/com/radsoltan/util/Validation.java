package com.radsoltan.util;

import com.radsoltan.dto.ReportInfoDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static final String THREE_WORD_ADDRESS_REGEX = "^/*(?:(?:\\p{L}\\p{M}*)+[.｡。･・︒។։။۔።।](?:\\p{L}\\p{M}*)+[.｡。･・︒។։။۔።।](?:\\p{L}\\p{M}*)+|(?:\\p{L}\\p{M}*)+([\u0020\u00A0](?:\\p{L}\\p{M}*)+){1,3}[.｡。･・︒។։။۔።।](?:\\p{L}\\p{M}*)+([\u0020\u00A0](?:\\p{L}\\p{M}*)+){1,3}[.｡。･・︒។։။۔።।](?:\\p{L}\\p{M}*)+([\u0020\u00A0](?:\\p{L}\\p{M}*)+){1,3})$";

    private static boolean validateThreeWordAddress(String address) {
        if (address == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(THREE_WORD_ADDRESS_REGEX);
        Matcher matcher = pattern.matcher(address);

        return matcher.find();
    }

    public static boolean isValidReportInfo(ReportInfoDTO reportInfo) {
        return (reportInfo.getLatitude() != null && reportInfo.getLongitude() != null) || validateThreeWordAddress(reportInfo.getThreeWordAddress());
    }
}

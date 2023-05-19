package com.radsoltan.util;

import com.radsoltan.dto.ReportInfoDTO;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class includes number of methods used for the validation of the 3 word address.
 */
public class Validation {
    private static final String THREE_WORD_ADDRESS_REGEX = "^/*(?:(?:\\p{L}\\p{M}*)+[.｡。･・︒។։။۔።।](?:\\p{L}\\p{M}*)+[.｡。･・︒។։။۔።।](?:\\p{L}\\p{M}*)+|(?:\\p{L}\\p{M}*)+([\u0020\u00A0](?:\\p{L}\\p{M}*)+){1,3}[.｡。･・︒។։။۔።।](?:\\p{L}\\p{M}*)+([\u0020\u00A0](?:\\p{L}\\p{M}*)+){1,3}[.｡。･・︒។։။۔።।](?:\\p{L}\\p{M}*)+([\u0020\u00A0](?:\\p{L}\\p{M}*)+){1,3})$";

    /**
     * Validates if provided address matches a format required for 3 word address.
     * See: https://developer.what3words.com/tutorial/detecting-if-text-is-in-the-format-of-a-3-word-address
     *
     * @param address address to be checked
     * @return true if {@code address} matches the format
     */
    public static boolean validateThreeWordAddress(String address) {
        Pattern pattern = Pattern.compile(THREE_WORD_ADDRESS_REGEX);
        Matcher matcher = pattern.matcher(address);

        return matcher.find();
    }

    /**
     * Checks if provided report info is valid i.e. it must have either coordinates or 3wa address provided.
     *
     * @param reportInfo report info
     * @return true if either coordinates or 3wa address is provided
     */
    public static boolean isValidReportInfo(ReportInfoDTO reportInfo) {
        return (reportInfo.getLatitude() != null && reportInfo.getLongitude() != null) || StringUtils.hasText(reportInfo.getThreeWordAddress());
    }

    /**
     * Checks if all fields in report information are filled.
     *
     * @param reportInfo report info
     * @return true if all fields are filled in
     */
    public static boolean isReportInfoComplete(ReportInfoDTO reportInfo) {
        return reportInfo.getLatitude() != null && reportInfo.getLongitude() != null && validateThreeWordAddress(reportInfo.getThreeWordAddress());
    }
}

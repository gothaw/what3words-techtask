package com.radsoltan.exception;

import com.radsoltan.util.Constants;

/**
 * Exception that should be thrown when a report request includes both 3wa and coordinates, but they do not correspond to the same place.
 */
public class InvalidReportInformationException extends RuntimeException {
    public InvalidReportInformationException() {
        super(Constants.INVALID_REPORT_INFORMATION_REQUEST);
    }
}

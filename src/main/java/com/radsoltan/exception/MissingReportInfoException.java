package com.radsoltan.exception;

import com.radsoltan.util.Constants;

/**
 * Exception that should be thrown when report information has neither 3 word address nor complete set of coordinates.
 */
public class MissingReportInfoException extends RuntimeException {
    public MissingReportInfoException() {
        super(Constants.MISSING_REPORT_INFO);
    }
}

package com.radsoltan.exception;

import com.radsoltan.util.Constants;

public class MissingReportInfoException extends RuntimeException {
    public MissingReportInfoException() {
        super(Constants.MISSING_REPORT_INFO);
    }
}

package com.radsoltan.exception;

import com.radsoltan.util.Constants;

/**
 * Exception that is thrown when converting one w3a to another and no w3a was provided.
 */
public class MissingAddressInfoException extends RuntimeException {
    public MissingAddressInfoException() {
        super(Constants.MISSING_ADDRESS);
    }
}

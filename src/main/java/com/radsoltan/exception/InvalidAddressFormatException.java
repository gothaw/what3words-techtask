package com.radsoltan.exception;

import com.radsoltan.util.Constants;

/**
 * Exception that should be thrown when 3 word address does not match a regex pattern required for a valid 3 word address.
 */
public class InvalidAddressFormatException extends RuntimeException {
    public InvalidAddressFormatException() {
        super(Constants.INVALID_ADDRESS_FORMAT);
    }
}

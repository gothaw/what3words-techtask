package com.radsoltan.exception;

import com.radsoltan.util.Constants;

public class InvalidAddressFormatException extends RuntimeException {
    public InvalidAddressFormatException() {
        super(Constants.INVALID_ADDRESS_FORMAT);
    }
}

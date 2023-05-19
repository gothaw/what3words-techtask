package com.radsoltan.exception;

import com.radsoltan.util.Constants;

/**
 * Exception that should be thrown when converting one 3wa to another and the provided 3wa is not recognized.
 */
public class AddressNotRecognizedException extends RuntimeException {
    public AddressNotRecognizedException(String address) {
        super(Constants.THREE_WORD_ADDRESS_NOT_RECOGNIZED + address);
    }
}

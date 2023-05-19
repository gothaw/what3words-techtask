package com.radsoltan.exception;

import com.radsoltan.util.Constants;

/**
 * Exception that should be thrown if provided coordinates are not in the UK.
 */
public class LocationNotInUkException extends RuntimeException {
    public LocationNotInUkException() {
        super(Constants.NOT_UK_COORDINATES);
    }
}

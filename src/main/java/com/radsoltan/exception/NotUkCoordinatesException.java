package com.radsoltan.exception;

import com.radsoltan.util.Constants;

/**
 * Exception that should be thrown if provided coordinates are not in the UK.
 */
public class NotUkCoordinatesException extends RuntimeException {
    public NotUkCoordinatesException() {
        super(Constants.NOT_UK_COORDINATES);
    }
}

package com.radsoltan.error;

import com.radsoltan.exception.InvalidAddressFormatException;
import com.radsoltan.exception.MissingReportInfoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handler that handles all exceptions thrown by the controller.
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * Handles the exception for missing report information that is both 3 word address and complete set of coordinates are missing.
     *
     * @param exception MissingReportInfoException
     * @return Bad request response with an error message
     */
    @ExceptionHandler(MissingReportInfoException.class)
    public ResponseEntity<ErrorMessage> handleUnableToRetrieveDataException(MissingReportInfoException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    /**
     * Handles the exception when 3 word address does not match a regex pattern required for a valid 3 word address.
     *
     * @param exception InvalidAddressFormatException
     * @return Bad request response with an error message
     */
    @ExceptionHandler(InvalidAddressFormatException.class)
    public ResponseEntity<ErrorMessage> handleUnableToRetrieveDataException(InvalidAddressFormatException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
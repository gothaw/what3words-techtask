package com.radsoltan.error;

import com.radsoltan.exception.InvalidAddressFormatException;
import com.radsoltan.exception.MissingReportInfoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MissingReportInfoException.class)
    public ResponseEntity<ErrorMessage> handleUnableToRetrieveDataException(MissingReportInfoException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InvalidAddressFormatException.class)
    public ResponseEntity<ErrorMessage> handleUnableToRetrieveDataException(InvalidAddressFormatException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}

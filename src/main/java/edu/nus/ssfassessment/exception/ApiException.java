package edu.nus.ssfassessment.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime ZonedDateTime;

    public ApiException(String message,
                        HttpStatus httpStatus, java.time.ZonedDateTime zonedDateTime) {
        this.message = message;
        this.httpStatus = httpStatus;
        ZonedDateTime = zonedDateTime;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public java.time.ZonedDateTime getZonedDateTime() {
        return ZonedDateTime;
    }
}

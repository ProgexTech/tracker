package com.progex.tracker.exceptions;

import com.progex.tracker.exceptions.handler.TrackerAPIError;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class TrackerException extends RuntimeException {

    private TrackerAPIError trackerAPIError;
    private HttpStatus httpStatus;

    public TrackerException(int httpErrorCode, String message) {
        super(message);
        this.trackerAPIError = new TrackerAPIError(httpErrorCode, message);
        this.httpStatus = Optional.ofNullable(HttpStatus.resolve(httpErrorCode))
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public TrackerAPIError getTrackerAPIError() {
        return trackerAPIError;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}

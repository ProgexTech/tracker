package com.progex.tracker.exceptions.handler;

import com.progex.tracker.exceptions.TrackerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class APIExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(APIExceptionHandler.class);

    @ExceptionHandler(value = {TrackerException.class})
    public ResponseEntity<TrackerAPIError> handleException(TrackerException ex, WebRequest request) {

        LOGGER.error("[API] An error occurred: [{}]", ex.getTrackerAPIError().getErrorMessage());

        return new ResponseEntity<>(ex.getTrackerAPIError(), ex.getHttpStatus());

    }

}

package com.progex.tracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author indunil
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RestAPIEntityNotFoundException extends RuntimeException{
    public RestAPIEntityNotFoundException(String msg){
        super(msg);
    }
}

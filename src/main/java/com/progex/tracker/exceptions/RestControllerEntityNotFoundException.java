package com.progex.tracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author indunil
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RestControllerEntityNotFoundException extends RuntimeException{
    public RestControllerEntityNotFoundException(String msg){
        super(msg);
    }
}

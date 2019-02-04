package com.progex.tracker.exceptions;

/**
 * @author indunil
 */
public class EntityNotFoundException extends TrackerException {

    public EntityNotFoundException(String s) {
        super(404, s);
    }

}

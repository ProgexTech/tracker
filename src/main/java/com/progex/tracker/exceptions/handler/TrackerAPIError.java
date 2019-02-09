package com.progex.tracker.exceptions.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackerAPIError {

    static final long serialVersionUID = 1L;

    private int errorCode;
    private String errorMessage;

}

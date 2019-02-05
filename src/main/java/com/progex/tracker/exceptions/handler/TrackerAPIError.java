package com.progex.tracker.exceptions.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackerAPIError {

    private int errorCode;
    private String errorMessage;

}

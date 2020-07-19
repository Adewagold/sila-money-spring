package com.sila.eth.response;

/**
 * *  Created by Adewale Adeleye on 2019-09-26
 **/
public class ErrorResponse extends Exception {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorResponse(String responseBodyAsString) {
        this.errorMessage = responseBodyAsString;
    }
}

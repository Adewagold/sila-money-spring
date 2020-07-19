package com.sila.eth.response;

/**
 * *  Created by Adewale Adeleye on 2019-09-21
 **/
public class MainResponseParam extends BaseResponse{
    private String message;
    private String reference;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String response) {
        this.reference = response;
    }
}

package com.sila.eth.response;

/**
 * *  Created by Adewale Adeleye on 2019-09-29
 **/
public class RequestKycResponse extends MainResponseParam {
    private String external_request;

    public String getExternal_request() {
        return external_request;
    }

    public void setExternal_request(String external_request) {
        this.external_request = external_request;
    }
}

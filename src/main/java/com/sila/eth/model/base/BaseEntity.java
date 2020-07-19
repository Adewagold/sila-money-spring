package com.sila.eth.model.base;

/**
 * *  Created by Adewale Adeleye on 2019-09-21
 **/
public class BaseEntity {
    private Header header;
    private String message;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


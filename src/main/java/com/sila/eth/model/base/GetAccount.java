package com.sila.eth.model.base;

import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * *  Created by Adewale Adeleye on 2019-11-06
 **/
public class GetAccount extends BaseEntity {
    @JsonSetter("user_handle")
    private String userHandle;
    private String message;

    public String getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}

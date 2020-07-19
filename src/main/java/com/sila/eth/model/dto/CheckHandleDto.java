package com.sila.eth.model.dto;

import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * *  Created by Adewale Adeleye on 2019-09-22
 **/
public class CheckHandleDto {
    @JsonSetter("user_handle")
    private String userHandle;
    private String message;

    public String getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

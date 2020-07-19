package com.sila.eth.model.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.sila.eth.model.base.BaseEntity;

/**
 * *  Created by Adewale Adeleye on 2019-10-13
 **/
public class LinkAccountDto{
    private String public_token;
    private String account_name;
    @JsonSetter("user_handle")
    private String userHandle;
    private String message;

    public String getPublic_token() {
        return public_token;
    }

    public void setPublic_token(String public_token) {
        this.public_token = public_token;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

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

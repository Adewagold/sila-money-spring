package com.sila.eth.model.base;

import org.springframework.beans.factory.annotation.Value;

/**
 * *  Created by Adewale Adeleye on 2019-09-21
 **/
public class Header {
    private String created;
    private String auth_handle;
    private String user_handle;
    @Value("${sila.app.version}")
    private String version;
    @Value("${sila.crypto.type}")
    private String crypto;
    private String reference;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getAuth_handle() {
        return auth_handle;
    }

    public void setAuth_handle(String auth_handle) {
        this.auth_handle = auth_handle;
    }

    public String getUser_handle() {
        return user_handle;
    }

    public void setUser_handle(String user_handle) {
        this.user_handle = user_handle;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCrypto() {
        return crypto;
    }

    public void setCrypto(String crypto) {
        this.crypto = crypto;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}

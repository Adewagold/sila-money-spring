package com.sila.eth.model.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.sila.eth.model.register.*;

/**
 * *  Created by Adewale Adeleye on 2019-09-23
 **/
public class RegisterDto {
    @JsonSetter("user_handle")
    private String userHandle;
    private String message;
    private Address address;
    private Identity identity;
    private Contact contact;


    private CryptoEntry crypto_entry;
    private Entity entity;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
    @JsonGetter("crypto_entry")
    public CryptoEntry getCryptoEntry() {
        return crypto_entry;
    }

    public void setCryptoEntry(CryptoEntry crypto_entry) {
        this.crypto_entry = crypto_entry;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
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

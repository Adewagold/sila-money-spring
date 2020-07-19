package com.sila.eth.model.register;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sila.eth.model.base.BaseEntity;

/**
 * *  Created by Adewale Adeleye on 2019-09-23
 **/
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Register extends BaseEntity {
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
}

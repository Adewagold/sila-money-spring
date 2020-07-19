package com.sila.eth.model.register;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * *  Created by Adewale Adeleye on 2019-09-23
 **/
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Identity {
    private String identity_alias, identity_value;

    public String getIdentity_alias() {
        return identity_alias;
    }

    public void setIdentity_alias(String identity_alias) {
        this.identity_alias = identity_alias;
    }

    public String getIdentity_value() {
        return identity_value;
    }

    public void setIdentity_value(String identity_value) {
        this.identity_value = identity_value;
    }
}

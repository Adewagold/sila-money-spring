package com.sila.eth.model.register;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * *  Created by Adewale Adeleye on 2019-09-23
 **/
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CryptoEntry {
    private String crypto_alias, crypto_address,crypto_code;

    public String getCrypto_alias() {
        return crypto_alias;
    }

    public void setCrypto_alias(String crypto_alias) {
        this.crypto_alias = crypto_alias;
    }

    public String getCrypto_address() {
        return crypto_address;
    }

    public void setCrypto_address(String crypto_address) {
        this.crypto_address = crypto_address;
    }

    public String getCrypto_code() {
        return crypto_code;
    }

    public void setCrypto_code(String crypto_code) {
        this.crypto_code = crypto_code;
    }
}

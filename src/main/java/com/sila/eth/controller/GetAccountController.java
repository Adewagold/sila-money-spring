package com.sila.eth.controller;

import com.sila.eth.model.base.GetAccount;
import com.sila.eth.model.dto.LinkAccountDto;
import com.sila.eth.response.ErrorResponse;
import com.sila.eth.service.GetAccountService;
import com.sila.eth.service.LinkAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * *  Created by Adewale Adeleye on 2019-10-13
 **/
@RestController
@RequestMapping("/api/v1/")
public class GetAccountController {
    private GetAccountService getAccountService;

    @Autowired
    public GetAccountController(GetAccountService getAccountService) {
        this.getAccountService = getAccountService;
    }
    @PostMapping(value = "get_account", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAccount(@RequestBody GetAccount getAccount) throws IOException, ErrorResponse {
        return new ResponseEntity<>(getAccountService.getAccount(getAccount), HttpStatus.OK);
    }
}

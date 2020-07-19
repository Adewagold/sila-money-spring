package com.sila.eth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sila.eth.model.dto.CheckHandleDto;
import com.sila.eth.model.dto.LinkAccountDto;
import com.sila.eth.response.ErrorResponse;
import com.sila.eth.service.LinkAccountService;
import com.sila.eth.service.RequestKycService;
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
public class LinkAccountController {
    private LinkAccountService linkAccountService;

    @Autowired
    public LinkAccountController(LinkAccountService linkAccountService) {
        this.linkAccountService = linkAccountService;
    }
    @PostMapping(value = "link_account", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> linkaccount(@RequestBody LinkAccountDto linkAccountDto) throws IOException, ErrorResponse {
        return new ResponseEntity<>(linkAccountService.linkAccount(linkAccountDto), HttpStatus.OK);
    }
}

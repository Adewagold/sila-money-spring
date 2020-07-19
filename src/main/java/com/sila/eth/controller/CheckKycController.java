package com.sila.eth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sila.eth.model.dto.CheckHandleDto;
import com.sila.eth.service.CheckKycService;
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
 * *  Created by Adewale Adeleye on 2019-09-29
 **/
@RestController
@RequestMapping("/api/v1/check_kyc")
public class CheckKycController {
    private CheckKycService checkKycService;

    @Autowired
    public CheckKycController(CheckKycService checkKycService) {
        this.checkKycService = checkKycService;
    }
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkHandle(@RequestBody CheckHandleDto checkHandle) throws IOException {
        return new ResponseEntity<>(checkKycService.checkHandleResponse(checkHandle), HttpStatus.OK);
    }
}

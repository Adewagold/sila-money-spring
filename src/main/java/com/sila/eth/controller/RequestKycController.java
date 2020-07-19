package com.sila.eth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sila.eth.model.dto.CheckHandleDto;
import com.sila.eth.response.CheckHandleResponse;
import com.sila.eth.service.CheckHandleService;
import com.sila.eth.service.RequestKycService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * *  Created by Adewale Adeleye on 2019-09-27
 **/
@RestController
@RequestMapping("/api/v1/request_kyc")
public class RequestKycController {
    private RequestKycService requestKycService;

    @Autowired
    public RequestKycController(RequestKycService requestKycService) {
        this.requestKycService = requestKycService;
    }
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkHandle(@RequestBody CheckHandleDto checkHandle) throws JsonProcessingException {
        return new ResponseEntity<>(requestKycService.checkHandleResponse(checkHandle), HttpStatus.OK);
    }
}

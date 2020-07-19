package com.sila.eth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sila.eth.model.dto.CheckHandleDto;
import com.sila.eth.model.dto.RegisterDto;
import com.sila.eth.response.CheckHandleResponse;
import com.sila.eth.response.ErrorResponse;
import com.sila.eth.response.RegisterResponse;
import com.sila.eth.service.CheckHandleService;
import com.sila.eth.service.RegisterService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * *  Created by Adewale Adeleye on 2019-09-26
 **/
@RestController
@RequestMapping("/api/v1/")
public class RegisterController {
    private RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping(value = "register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterResponse> checkHandle(@RequestBody RegisterDto registerDto) throws JsonProcessingException, ErrorResponse, JSONException {
        return new ResponseEntity<>(registerService.register(registerDto), HttpStatus.OK);
    }
}

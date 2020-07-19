package com.sila.eth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sila.eth.model.dto.RegisterDto;
import com.sila.eth.response.ErrorResponse;
import com.sila.eth.response.RegisterResponse;
import org.json.JSONException;

/**
 * *  Created by Adewale Adeleye on 2019-09-26
 **/
public interface RegisterService {
    RegisterResponse register(RegisterDto registerDto) throws JsonProcessingException, ErrorResponse, JSONException;
}

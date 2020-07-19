package com.sila.eth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sila.eth.model.dto.CheckHandleDto;
import com.sila.eth.response.RequestKycResponse;

import java.io.IOException;

/**
 * *  Created by Adewale Adeleye on 2019-09-29
 **/
public interface CheckKycService {
    RequestKycResponse checkHandleResponse(CheckHandleDto checkHandle) throws IOException;
}

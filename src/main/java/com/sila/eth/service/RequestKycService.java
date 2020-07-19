package com.sila.eth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sila.eth.model.dto.CheckHandleDto;
import com.sila.eth.response.RequestKycResponse;

/**
 * *  Created by Adewale Adeleye on 2019-09-27
 **/
public interface RequestKycService {
    RequestKycResponse checkHandleResponse(CheckHandleDto checkHandle) throws JsonProcessingException;
}

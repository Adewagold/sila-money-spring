package com.sila.eth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sila.eth.model.dto.CheckHandleDto;
import com.sila.eth.response.CheckHandleResponse;

/**
 * *  Created by Adewale Adeleye on 2019-09-21
 **/
public interface CheckHandleService {
    CheckHandleResponse checkHandleResponse(CheckHandleDto checkHandle) throws JsonProcessingException;

}

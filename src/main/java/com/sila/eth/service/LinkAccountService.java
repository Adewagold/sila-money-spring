package com.sila.eth.service;

import com.sila.eth.model.dto.CheckHandleDto;
import com.sila.eth.model.dto.LinkAccountDto;
import com.sila.eth.response.ErrorResponse;
import com.sila.eth.response.LinkAccountResponse;
import com.sila.eth.response.RequestKycResponse;

import java.io.IOException;

/**
 * *  Created by Adewale Adeleye on 2019-10-13
 **/
public interface LinkAccountService {
    LinkAccountResponse linkAccount(LinkAccountDto linkAccountDto) throws IOException, ErrorResponse;
}

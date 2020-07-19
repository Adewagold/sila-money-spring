package com.sila.eth.service;

import com.sila.eth.model.base.GetAccount;
import com.sila.eth.model.dto.LinkAccountDto;
import com.sila.eth.response.ErrorResponse;
import com.sila.eth.response.GetAccountResponse;
import com.sila.eth.response.LinkAccountResponse;

import java.io.IOException;
import java.util.List;

/**
 * *  Created by Adewale Adeleye on 2019-10-26
 **/
public interface GetAccountService {
    List<GetAccountResponse> getAccount(GetAccount linkAccountDto) throws IOException, ErrorResponse;
}

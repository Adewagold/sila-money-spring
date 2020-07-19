package com.sila.eth.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sila.eth.model.base.GetAccount;
import com.sila.eth.model.base.Header;
import com.sila.eth.response.ErrorResponse;
import com.sila.eth.response.GetAccountResponse;
import com.sila.eth.service.GetAccountService;
import com.sila.eth.util.BaseUtil;
import com.sila.eth.util.SilaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * *  Created by Adewale Adeleye on 2019-10-26
 **/
@Service
public class GetAccountServiceImpl implements GetAccountService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckHandleServiceImpl.class);
    @Autowired
    RestTemplate restTemplate;

    @Value("${sila.get.account}")
    private String getAccount;

    @Value("${sila.auth.handle}")
    private String authHandle;

    @Value("${sila.app.version}")
    private String version;

    @Value("${sila.crypto.type}")
    private String crypto;

    @Override
    public List<GetAccountResponse> getAccount(GetAccount getAccount) throws IOException, ErrorResponse {
        Header header = new Header();
        header.setAuth_handle(authHandle);
        header.setUser_handle(getAccount.getUserHandle());
        header.setVersion(version);
        header.setReference(UUID.randomUUID().toString());
        header.setCreated(BaseUtil.getEpoch());
        header.setCrypto(crypto);

        getAccount.setHeader(header);
        return makeRequest(getAccount);
    }

    private List<GetAccountResponse> makeRequest(GetAccount register) throws JsonProcessingException, ErrorResponse {
        String signature = signRequest(register);
        String userSignature = getUserSignature(register);

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type","application/json");
        headers.set("Access-Control-Allow-Origin","*");
        headers.set("Access-Control-Max-Age",String.valueOf(3600));
        headers.set("authSignature",signature);
        headers.set("usersignature",userSignature);
        HttpEntity<GetAccount> responsePostEntity = new HttpEntity<>(register,headers);

        ResponseEntity<List<GetAccountResponse>> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(getAccount, HttpMethod.POST, responsePostEntity, new ParameterizedTypeReference<List<GetAccountResponse>>() {});
        }
        catch (HttpStatusCodeException e){
            LOGGER.info(String.valueOf(ResponseEntity.status(e.getStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString())));
            throw new ErrorResponse(
                    ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString()).toString());
        }
        List<GetAccountResponse> response = responseEntity.getBody();
        LOGGER.info("Response - ",responseEntity.getBody().toString());
        return response;
    }

    private String signRequest(GetAccount register) throws JsonProcessingException {
        String stringMessage = mapper.writeValueAsString(register);
        LOGGER.info(String.format("Request - %s", stringMessage));
        return SilaUtil.getSignature(stringMessage);
    }


    private String getUserSignature(GetAccount linkAccount) throws JsonProcessingException {
        String stringMessage = mapper.writeValueAsString(linkAccount);
        LOGGER.info(stringMessage);
        return SilaUtil.getUserSignature(stringMessage);
    }
}

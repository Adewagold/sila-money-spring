package com.sila.eth.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sila.eth.model.base.Header;
import com.sila.eth.model.checkhandle.CheckHandle;
import com.sila.eth.model.dto.CheckHandleDto;
import com.sila.eth.response.RegisterResponse;
import com.sila.eth.response.RequestKycResponse;
import com.sila.eth.service.RequestKycService;
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

import java.lang.reflect.Type;
import java.util.UUID;

/**
 * *  Created by Adewale Adeleye on 2019-09-27
 **/
@Service
public class RequestKycServiceImpl implements RequestKycService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckHandleServiceImpl.class);
    @Autowired
    RestTemplate restTemplate;

    @Value("${sila.request_kyc}")
    private String checkHandleUrl;

    @Value("${sila.auth.handle}")
    private String authHandle;

    @Value("${sila.app.version}")
    private String version;

    @Value("${sila.crypto.type}")
    private String crypto;

    @Override
    public RequestKycResponse checkHandleResponse(CheckHandleDto checkHandle) throws JsonProcessingException {
        CheckHandle handle = new CheckHandle();
        Header header = new Header();
        header.setAuth_handle(authHandle);
        header.setUser_handle(checkHandle.getUserHandle());
        header.setVersion(version);
        header.setReference(UUID.randomUUID().toString());
        header.setCreated(BaseUtil.getEpoch());
        header.setCrypto(crypto);

        handle.setHeader(header);
        handle.setMessage(checkHandle.getMessage());

        return makeRequest(handle);
    }

    private String signRequest(CheckHandle checkHandle) throws JsonProcessingException {
        String stringMessage = mapper.writeValueAsString(checkHandle);
        LOGGER.info(stringMessage);
        return SilaUtil.getSignature(stringMessage);
    }

    private String getUserSignature(CheckHandle checkHandle) throws JsonProcessingException {
        String stringMessage = mapper.writeValueAsString(checkHandle);
        LOGGER.info(stringMessage);
        return SilaUtil.getUserSignature(stringMessage);
    }

    private RequestKycResponse makeRequest(CheckHandle checkHandle) throws JsonProcessingException {
        String signature = signRequest(checkHandle);
        String userSignature = getUserSignature(checkHandle);

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type","application/json");
        headers.set("Access-Control-Allow-Origin","*");
        headers.set("Access-Control-Max-Age",String.valueOf(3600));
        headers.set("authSignature",signature);
        headers.set("usersignature",userSignature);
        HttpEntity<CheckHandle> responsePostEntity = new HttpEntity<>(checkHandle,headers);

        ResponseEntity<RequestKycResponse> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(checkHandleUrl, HttpMethod.POST, responsePostEntity, new ParameterizedTypeReference<RequestKycResponse>(){});
            if(responseEntity.getStatusCode().is2xxSuccessful()){
                RequestKycResponse response = responseEntity.getBody();
                return response;
            }
            if(responseEntity.getStatusCode().is4xxClientError()){

            }
        }
        catch (HttpStatusCodeException e){
            LOGGER.info(String.valueOf(ResponseEntity.status(e.getStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString())));
        }
        return responseEntity.getBody();
    }
}

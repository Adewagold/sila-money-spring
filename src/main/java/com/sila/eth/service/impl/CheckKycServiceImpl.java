package com.sila.eth.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sila.eth.model.base.Header;
import com.sila.eth.model.checkhandle.CheckHandle;
import com.sila.eth.model.dto.CheckHandleDto;
import com.sila.eth.response.RequestKycResponse;
import com.sila.eth.service.CheckKycService;
import com.sila.eth.util.BaseUtil;
import com.sila.eth.util.RequestUtil;
import com.sila.eth.util.SilaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.UUID;

/**
 * *  Created by Adewale Adeleye on 2019-09-29
 **/
@Service
public class CheckKycServiceImpl implements CheckKycService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckKycServiceImpl.class);
    @Autowired
    RestTemplate restTemplate;

    @Value("${sila.check.kyc}")
    private String checkKycUrl;

    @Value("${sila.auth.handle}")
    private String authHandle;

    @Value("${sila.app.version}")
    private String version;

    @Value("${sila.crypto.type}")
    private String crypto;

    @Override
    public RequestKycResponse checkHandleResponse(CheckHandleDto checkHandle) throws IOException {
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

        //Header
        String signature = signRequest(handle);
        String userSignature = getUserSignature(handle);

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type","application/json");
        headers.set("Access-Control-Allow-Origin","*");
        headers.set("Access-Control-Max-Age",String.valueOf(3600));
        headers.set("authSignature",signature);
        headers.set("usersignature",userSignature);
        HttpEntity<CheckHandle> responsePostEntity = new HttpEntity<>(handle,headers);

        ResponseEntity<String> entity = RequestUtil.makeRequest(checkKycUrl,responsePostEntity);
        if(entity.getStatusCode().is2xxSuccessful()){
            String requestResponse = entity.getBody();
            RequestKycResponse response = mapper.readValue(requestResponse,RequestKycResponse.class);
            return response;
        }
        if(entity.getStatusCode().is4xxClientError()){
            //Throw some exception
            return null;
        }
        return null;
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
}

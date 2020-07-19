package com.sila.eth.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sila.eth.model.base.Header;
import com.sila.eth.model.checkhandle.CheckHandle;
import com.sila.eth.model.dto.LinkAccountDto;
import com.sila.eth.model.link_account.LinkAccount;
import com.sila.eth.response.ErrorResponse;
import com.sila.eth.response.LinkAccountResponse;
import com.sila.eth.service.LinkAccountService;
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
import java.util.UUID;

/**
 * *  Created by Adewale Adeleye on 2019-10-13
 **/
@Service
public class LinkAccountImpl implements LinkAccountService {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckHandleServiceImpl.class);
    @Autowired
    RestTemplate restTemplate;

    @Value("${sila.link.account}")
    private String linkAccount;

    @Value("${sila.auth.handle}")
    private String authHandle;

    @Value("${sila.app.version}")
    private String version;

    @Value("${sila.crypto.type}")
    private String crypto;

    @Override
    public LinkAccountResponse linkAccount(LinkAccountDto linkAccountDto) throws IOException, ErrorResponse {
        Header header = new Header();
        header.setAuth_handle(authHandle);
        header.setUser_handle(linkAccountDto.getUserHandle());
        header.setVersion(version);
        header.setReference(UUID.randomUUID().toString());
        header.setCreated(BaseUtil.getEpoch());
        header.setCrypto(crypto);


        LinkAccount linkAccount = new LinkAccount();
        linkAccount.setAccount_name(linkAccountDto.getAccount_name());
        linkAccount.setUserHandle(linkAccountDto.getUserHandle());
        linkAccount.setPublic_token(linkAccountDto.getPublic_token());
        linkAccount.setMessage(linkAccountDto.getMessage());
        linkAccount.setHeader(header);
        return makeRequest(linkAccount);
    }

    private LinkAccountResponse makeRequest(LinkAccount register) throws JsonProcessingException, ErrorResponse {
        String signature = signRequest(register);
        String userSignature = getUserSignature(register);

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type","application/json");
        headers.set("Access-Control-Allow-Origin","*");
        headers.set("Access-Control-Max-Age",String.valueOf(3600));
        headers.set("authSignature",signature);
        headers.set("usersignature",userSignature);
        HttpEntity<LinkAccount> responsePostEntity = new HttpEntity<>(register,headers);

        ResponseEntity<LinkAccountResponse> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(linkAccount, HttpMethod.POST, responsePostEntity, new ParameterizedTypeReference<LinkAccountResponse>() {});
        }
        catch (HttpStatusCodeException e){
            LOGGER.info(String.valueOf(ResponseEntity.status(e.getStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString())));
            throw new ErrorResponse(
                    ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString()).toString());
        }
        LinkAccountResponse response = responseEntity.getBody();
        LOGGER.info("Response - ",responseEntity.getBody().toString());
        return response;
    }

    private String signRequest(LinkAccount register) throws JsonProcessingException {
        String stringMessage = mapper.writeValueAsString(register);
        LOGGER.info(String.format("Request - %s", stringMessage));
        return SilaUtil.getSignature(stringMessage);
    }


    private String getUserSignature(LinkAccount linkAccount) throws JsonProcessingException {
        String stringMessage = mapper.writeValueAsString(linkAccount);
        LOGGER.info(stringMessage);
        return SilaUtil.getUserSignature(stringMessage);
    }
}

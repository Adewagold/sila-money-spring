package com.sila.eth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sila.eth.model.checkhandle.CheckHandle;
import com.sila.eth.response.RequestKycResponse;
import com.sila.eth.service.impl.CheckHandleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * *  Created by Adewale Adeleye on 2019-09-21
 **/
public class RequestUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestUtil.class);

    public RequestUtil() {
    }

    //Request Utility for SDK
    public static ResponseEntity<String> makeRequest(String url, HttpEntity responsePostEntity) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, responsePostEntity, String.class);
            LOGGER.info(responseEntity.getBody());
            return responseEntity;
        }
        catch (HttpStatusCodeException e){
            LOGGER.info(String.valueOf(ResponseEntity.status(e.getStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString())));
        }
        return responseEntity;
    }
}

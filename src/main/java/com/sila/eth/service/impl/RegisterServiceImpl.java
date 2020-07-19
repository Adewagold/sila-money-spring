package com.sila.eth.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sila.eth.model.base.Header;
import com.sila.eth.model.dto.RegisterDto;
import com.sila.eth.model.register.Register;
import com.sila.eth.response.ErrorResponse;
import com.sila.eth.response.RegisterResponse;
import com.sila.eth.service.RegisterService;
import com.sila.eth.util.BaseUtil;
import com.sila.eth.util.SilaUtil;
import org.json.JSONException;
import org.json.JSONObject;
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
import org.web3j.crypto.*;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.UUID;

/**
 * *  Created by Adewale Adeleye on 2019-09-26
 **/
@Service
public class RegisterServiceImpl implements RegisterService {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckHandleServiceImpl.class);
    @Autowired
    RestTemplate restTemplate;

    @Value("${sila.register}")
    private String registerHandle;

    @Value("${sila.auth.handle}")
    private String authHandle;

    @Value("${sila.app.version}")
    private String version;

    @Value("${sila.crypto.type}")
    private String crypto;


    @Override
    public RegisterResponse register(RegisterDto registerDto) throws JsonProcessingException, ErrorResponse, JSONException {
        Register register = new Register();
        Header header = new Header();
        header.setAuth_handle(authHandle);
        header.setUser_handle(registerDto.getUserHandle());
        header.setVersion(version);
        header.setReference(UUID.randomUUID().toString());
        header.setCreated(BaseUtil.getEpoch());
        header.setCrypto(crypto);

        //Generate new ECDSA key and address
        JSONObject jsonObject = processKeyAddress(registerDto.getContact().getEmail());
        String address = jsonObject.getString("address");
        registerDto.getCryptoEntry().setCrypto_address(address);
        LOGGER.info("Private key is "+ jsonObject.getString("privatekey"));
        LOGGER.info("Address is "+ jsonObject.getString("privatekey"));

        register.setAddress(registerDto.getAddress());
        register.setIdentity(registerDto.getIdentity());
        register.setContact(registerDto.getContact());
        register.setEntity(registerDto.getEntity());
        register.setCryptoEntry(registerDto.getCryptoEntry());
        register.setHeader(header);
        register.setMessage(registerDto.getMessage());

        return makeRequest(register);
    }

    private String signRequest(Register register) throws JsonProcessingException {
        String stringMessage = mapper.writeValueAsString(register);
        LOGGER.info(String.format("Request - %s", stringMessage));
        return SilaUtil.getSignature(stringMessage);
    }

    private RegisterResponse makeRequest(Register register) throws JsonProcessingException, ErrorResponse {
        String signature = signRequest(register);

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type","application/json");
        headers.set("Access-Control-Allow-Origin","*");
        headers.set("Access-Control-Max-Age",String.valueOf(3600));
        headers.set("authSignature",signature);
        HttpEntity<Register> responsePostEntity = new HttpEntity<>(register,headers);

        ResponseEntity<RegisterResponse> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(registerHandle, HttpMethod.POST, responsePostEntity, new ParameterizedTypeReference<RegisterResponse>() {});
        }
        catch (HttpStatusCodeException e){
            LOGGER.info(String.valueOf(ResponseEntity.status(e.getStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString())));
            throw new ErrorResponse(
                    ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString()).toString());
        }
        RegisterResponse response = responseEntity.getBody();
        LOGGER.info("Response - ",responseEntity.getBody().toString());
        return response;
    }


    private static JSONObject processKeyAddress(String seed){

        JSONObject processJson = new JSONObject();

        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

            String sPrivatekeyInHex = privateKeyInDec.toString(16);

            WalletFile aWallet = Wallet.createLight(seed, ecKeyPair);
            String sAddress = aWallet.getAddress();


            processJson.put("address", "0x" + sAddress);
            processJson.put("privatekey", sPrivatekeyInHex);


        } catch (CipherException e) {
            //
        } catch (InvalidAlgorithmParameterException e) {
            //
        } catch (NoSuchAlgorithmException e) {
            //
        } catch (NoSuchProviderException e) {
            //
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return processJson;
    }

}

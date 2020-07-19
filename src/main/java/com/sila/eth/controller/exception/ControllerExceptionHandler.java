package com.sila.eth.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * *  Created by Adewale Adeleye on 2019-09-21
 **/
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.ACCEPTED)
    @ExceptionHandler(Exception.class)
    public String exception(Exception ex){
        return ex.getMessage();
    }
}

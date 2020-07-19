package com.sila.eth.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * *  Created by Adewale Adeleye on 2019-12-18
 **/
@RestController
public class CastilleController {
@RequestMapping(value = "/{text:[a-z-]+}.{number:[\\d]+}")
    public String yay(@PathVariable String text, @PathVariable Integer number){
    return "yaay"+ text+" "+number;
}
}

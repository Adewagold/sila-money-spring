package com.sila.eth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * *  Created by Adewale Adeleye on 2019-09-21
 **/
@Configuration
public class appConfig {
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

package com.example.ec_payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.openpix.sdk.WooviSDK;

@Configuration
public class WooviSdkConfig {

    @Value("${my.app.id}")
    private String appId;

    @Bean
    public WooviSDK wooviSDK() {
        return new WooviSDK(appId);
    }
    
}
